# Java 后端编程规范

## 概述

本文档定义 e-store 项目 Java 后端的分层架构规范和调用规则，确保代码结构清晰、职责明确、易于维护。

## 核心原则

1. **Controller 层只能调用 Service 层**：Controller 负责接收请求、参数校验、调用 Service 并返回响应，不能直接调用 Mapper 层。
2. **Service 层只能调用 BizMapper 层**：Service 负责业务逻辑处理，只能调用 BizMapper 进行数据访问，不能直接调用底层 Mapper。
3. **Controller 接收参数规范**：Controller 接收的对象必须以 `In` 结尾，放在 `vo/in` 目录。
4. **Controller 返回对象规范**：Controller 返回给前端的对象只能是 VO，放在 `vo/out` 目录。
5. **Service 层只能操作 DTO**：Service 不得直接读写 PO，Service 与 BizMapper 之间仅传递 DTO（及分页 DTO）。
6. **BizMapper 层返回对象规范**：BizMapper 对 Service 暴露的方法返回值只能是 DTO（或 `PageDTO<DTO>`），不能返回 PO。
7. **模型转换规范**：VO / DTO / PO 之间的转换必须通过 Convert（MapStruct）完成，禁止手写字段拷贝。
8. **禁止魔法值**：代码中不得出现未命名的数字、字符串等字面量；常量统一收敛到 `dao/constant/*Constants`（错误码枚举项 + 业务静态常量）。
9. **批处理规范**：禁止在循环内逐条查询或逐条写入；必须使用「批量查询 + 批量写入」，可按批拆分后每批内批量操作。
10. **注释规范**：新增代码注释必须使用「中文 + 英文」双语注释；新增枚举必须包含 JavaDoc（类注释 + 枚举项注释）。

## 分层架构

```
┌─────────────────┐
│   Controller    │  ← 接收HTTP请求，参数校验，返回响应
└────────┬────────┘
         │ 只能调用
         ↓
┌─────────────────┐
│     Service     │  ← 业务逻辑处理
└────────┬────────┘
         │ 只能调用
         ↓
┌─────────────────┐
│    BizMapper    │  ← 业务数据访问封装
└────────┬────────┘
         │ 调用
         ↓
┌─────────────────┐
│     Mapper      │  ← MyBatis Mapper（底层数据访问）
└─────────────────┘
```

## 各层职责说明

### 1. Controller 层

**职责**:
- 接收 HTTP 请求
- 参数校验（使用 `@Validated` 注解）
- 获取用户上下文（如 `RequestContext.getUserId()`）
- 调用 Service 层方法
- 封装响应结果（使用 `Out.success()` 或 `Out.fail()`）
- 异常处理（由全局异常处理器统一处理）

**VO 对象规范**:
- **接收参数**：必须放在 `vo/in` 目录，类名以 `In` 结尾（如 `StudyDurationIn`）
- **返回对象**：必须放在 `vo/out` 目录，返回类型必须是 VO（如 `StudyDurationVO`）
- 方法返回类型：`Out<VO>` 或 `Out<List<VO>>`

**禁止**:
- 直接调用 BizMapper 或 Mapper
- 在 Controller 中编写业务逻辑
- 接收参数对象不使用 `In` 结尾
- 返回对象不使用 VO

### 2. Service 层

**职责**:
- 实现业务逻辑
- 通过 Convert 做数据转换（VO ↔ DTO）
- 调用 BizMapper 进行数据访问
- 事务管理（使用 `@Transactional` 注解）
- 调用其他 Service（如需要）

**禁止**:
- 直接调用底层 Mapper
- 直接操作 PO（与 BizMapper 交互只能使用 DTO）
- 手写 VO/DTO/PO 字段拷贝

### 3. BizMapper 层

**职责**:
- 封装业务相关的数据访问逻辑
- 构建查询条件（使用 `LambdaQueryWrapper`）
- 调用底层 Mapper 执行数据库操作
- 通过 Convert 做数据转换（PO ↔ DTO）
- 处理软删除逻辑

**禁止**:
- 在 BizMapper 中编写业务逻辑
- 直接向 Service 返回 PO（必须返回 DTO）
- 手写 PO/DTO 字段拷贝

### 4. Mapper 层

**职责**:
- 定义 MyBatis Mapper 接口
- 提供基础的 CRUD 操作
- 执行 SQL 查询

**说明**：通常不直接调用，应通过 BizMapper 封装后使用。

### 5. Convert 层

**职责**:
- 负责 VO/DTO/PO 之间的结构转换
- 统一使用 MapStruct 定义转换接口

---

## 常量使用规范

### 原则

代码中**不得出现魔法值**（Magic Number / Magic String）：即未通过命名常量或枚举表达业务含义、直接写在逻辑中的数字/字符串字面量。

本项目常量统一规则：

1. **统一入口**：按领域集中到 `dao/constant/*Constants`。
2. **状态/类型优先枚举**：凡是**非 boolean** 且表达“状态、类型、模式、阶段”等业务语义的值，必须定义到 `dao/enums/*Enum`，禁止仅使用 `int` 常量承载。
3. **错误码**：使用实现 `ErrorCode` 的枚举常量（如 `ProductConstants.BRAND_NOT_EXISTS`）。
4. **业务常量**：放在同一个 `*Constants` 文件中作为 `public static final`（如默认值、阈值、批次大小等非枚举场景）。
5. **禁止裸值**：业务代码中禁止直接出现 `0/1/-1/2` 等业务语义字面量。

### 目录与命名

- 位置：`dao/src/main/java/com/followba/store/dao/constant/`
- 命名：`<Domain>Constants`，例如 `ProductConstants`
- 使用：`admin/service`、`admin/controller`、`admin/convert` 统一引用 `dao.constant` 下常量

### ProductConstants 标准样式

```java
public enum ProductConstants implements ErrorCode {
    BRAND_NOT_EXISTS(61001, "品牌不存在"),
    // ...
    ;

    private final int code;
    private final String msg;

    public static final int DEFAULT_ZERO = 0;
}
```

```java
public enum ProductSpuStatusEnum {
    ENABLE(0),
    DISABLE(1),
    RECYCLE(-1);
}
```

### 使用示例

```java
// ✅ 正确：错误码
throw new BizException(ProductConstants.BRAND_NOT_EXISTS);

// ✅ 正确：业务常量
int level = ProductConstants.CATEGORY_MIN_LEVEL;

// ✅ 正确：业务状态值（使用枚举）
spu.setStatus(ProductSpuStatusEnum.ENABLE.getCode());
```

### 错误示例

```java
// ❌ 错误：魔法值
spu.setStatus(0);
if (status == -1) { ... }

// ❌ 错误：常量分散在 admin、service、controller 各层
public final class ProductStatus { ... }
```

### 小结

| 场景 | 使用方式 | 示例 |
|------|----------|------|
| 业务错误码 | `dao.constant.*Constants` 枚举项 | `ProductConstants.SPU_NOT_EXISTS` |
| 业务状态/类型值（非 boolean） | `dao.enums.*Enum` | `ProductSpuStatusEnum.ENABLE.getCode()` |
| 业务固定值（非状态/类型） | `dao.constant.*Constants` 静态常量 | `ProductConstants.CATEGORY_MIN_LEVEL` |
| 避免 | 裸字面量、多处重复定义 | 不写 `0/1/-1` |

---

## 批处理规范

### 原则

涉及「多条数据的查询或写入」时，**禁止在循环中逐条查询或逐条写入**，必须使用批量接口，避免 N+1 查询与多次单条写入带来的性能问题。

1. **批量查询**：按 ID/条件集合一次性查询（如 `listByWords(words)`、`listExistingWords(overviewId, words)`），不在 for 中调用单条查询。
2. **批量写入**：将待写入数据收集为集合后一次性或分批批量插入（如 `batchAddWords`、`saveBatch`），不在 for 中逐条 insert。
3. **按批拆分**：若单次数据量过大，可先按固定大小拆批（如每批 500 条），**每批内部**仍使用批量查询、批量写入。

### 错误示例

```java
// ❌ 错误：循环内单条查询 + 单条插入
for (String word : in.getWords()) {
    if (bizSysDictionaryBaseMapper.getByWord(word) == null) continue;
    bizUserWordBookCustomWordMapper.addWord(in.getUserBookId(), word);
}
```

**问题**：N 个单词会触发 N 次字典查询 + 最多 N 次单条插入，数据库往返过多。

### 正确示例

```java
// ✅ 正确：按批拆分，每批内批量查询、批量写入
List<String> wordsTemp = in.getWords().stream().distinct().collect(Collectors.toList());
List<List<String>> listList = ListUtil.split(wordsTemp, 500);

for (List<String> words : listList) {
    // 批量查询已存在的单词
    Set<String> existingWords = new HashSet<>(
        bizUserWordBookCustomWordMapper.listExistingWords(in.getUserBookId(), words)
    );
    List<String> toAddWords = words.stream()
        .filter(w -> !existingWords.contains(w))
        .collect(Collectors.toList());

    // 批量查询字典中存在的单词
    List<SysDictionaryBaseDTO> dicExistWords = bizSysDictionaryBaseMapper.list(null, toAddWords);
    if (CollectionUtil.isEmpty(dicExistWords)) continue;

    List<String> needAddWords = dicExistWords.stream().map(SysDictionaryBaseDTO::getWord).toList();
    // 批量插入
    bizUserWordBookCustomWordMapper.batchAddWords(in.getUserBookId(), needAddWords);
}
```

**要点**：
- 每批内一次「批量查已存在」、一次「批量查字典」、一次「批量插入」。
- Biz 层提供批量接口，Service 层不出现循环单条调用。

### 小结

| 场景 | 禁止 | 推荐 |
|------|------|------|
| 多条查询 | 循环内 getByXxx、selectById | 按集合批量查询 listByXxx、listExistingWords |
| 多条写入 | 循环内 insert、addXxx | 批量写入 batchAddWords、saveBatch |
| 数据量大 | 单次全量查/写 | 按批拆分，每批内仍批量操作 |

---

## 注释规范

### 原则

1. 新增代码注释必须使用中文 + 英文双语表达。
2. 双语注释建议单行格式：`中文说明。 / English explanation.`
3. 注释应解释“为什么这样做”，避免描述显而易见的代码动作。
4. 新增枚举必须包含 JavaDoc（类注释 + 每个枚举项注释）。

### 示例

```java
// 先全量校验分类是否存在，避免部分成功。 / Pre-check all category IDs to avoid partial updates.
```

### 填充数据（Fill）模式（批处理扩展）

填充数据是指为列表数据填充关联信息（如场景图片、分类名称等）。本质是**批量查询关联数据**，必须遵循批处理规范。

**核心步骤**：
1. 提取所有需要查询的 ID（过滤 null、去重）
2. 一次性批量查询关联数据
3. 将查询结果构建为 Map 缓存
4. 遍历原始列表，从 Map 中取数据并填充

**标准模式**：定义 Fill 接口，VO/DTO 实现该接口，BizMapper 提供 `fill(list)` 方法，内部按上述步骤批量查询并填充。

---

## 正确示例

### Controller → Service → BizMapper

```java
// Controller
@PostMapping("/listOfStudyDuration")
public Out<List<StudyDurationVO>> listOfStudyDuration(@RequestBody @Validated StudyDurationIn in) {
    String userId = RequestContext.getUserId();
    return Out.success(speakUserStudyLogService.listOfStudyDuration(userId, in));
}

// Service
List<SpeakCharacterFeatureDTO> dtoList = bizSpeakCharacterFeatureMapper.list(in.getGender(), in.getNationality());
return SpeakCharacterFeatureConvert.INSTANCE.toOutList(dtoList);

// BizMapper
LambdaQueryWrapper<SpeakCharacterFeature> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(SpeakCharacterFeature::getDeleted, false);
List<SpeakCharacterFeature> poList = speakCharacterFeatureMapper.selectList(wrapper);
return SpeakCharacterFeatureConvert.INSTANCE.toDTOList(poList);
```

---

## 错误示例

| 错误 | 问题 |
|------|------|
| Controller 直接调用 BizMapper | 违反分层，业务逻辑混入 Controller |
| Service 直接调用底层 Mapper | 与数据访问耦合，难以复用 |
| Controller 中编写业务逻辑 | 违反单一职责，难以测试 |
| 返回 DTO 而非 VO、接收参数不以 In 结尾 | 违反 VO 规范 |
| 循环内单条查询/写入 | N+1 问题，性能差 |
| 使用魔法值（裸数字、字符串） | 可维护性差 |

---

## 检查清单

### Controller 层
- [ ] 只注入 Service，未注入 BizMapper 或 Mapper
- [ ] 接收参数以 `In` 结尾，放在 `vo/in` 目录
- [ ] 返回对象是 VO，放在 `vo/out` 目录
- [ ] 使用 `@Validated` 和 `Out.success()` / `Out.fail()`

### Service 层
- [ ] 只注入 BizMapper，未注入底层 Mapper
- [ ] 只操作 DTO，不直接读写 PO
- [ ] 数据转换通过 Convert 完成

### BizMapper 层
- [ ] 对 Service 暴露 DTO，不暴露 PO
- [ ] 处理软删除逻辑（`deleted = false`）

### 禁止魔法值
- [ ] 常量统一定义在 `dao/constant/*Constants`
- [ ] 非 boolean 且有业务语义的状态/类型值统一定义在 `dao/enums/*Enum`
- [ ] 错误码使用 `*Constants` 枚举项（`implements ErrorCode`）
- [ ] 业务固定值使用 `*Constants` 中 `public static final`
- [ ] 业务代码无裸字面量（`0/1/-1/2` 等）

### 批处理
- [ ] 无循环内单条查询
- [ ] 无循环内单条写入
- [ ] 数据量大时按批拆分，批内仍批量操作

---

## 常见问题

**Q: Service 可以调用其他 Service 吗？**  
A: 可以，需避免循环依赖，注意事务传播。

**Q: Controller 接收参数和返回对象的命名规范？**  
A: 接收参数以 `In` 结尾（`vo/in`），返回对象为 VO（`vo/out`）。

**Q: Service 与 BizMapper 之间用什么？**  
A: 仅 DTO 及 `PageDTO<DTO>`，不传 PO。

**Q: 填充数据时需要过滤软删除吗？**  
A: 按业务需求决定，在方法注释中说明。

---

## 总结

**核心原则**：
1. Controller → Service → BizMapper → Mapper
2. 接收参数 `In`，返回对象 VO
3. Service 与 BizMapper 间仅 DTO
4. 禁止魔法值，使用枚举和常量类
5. 禁止循环内单条查/写，必须批处理
