-- 开发阶段按新规则直接切换，无老数据兼容分支。 / Development switches directly to new rules without legacy-compat branches.
CREATE TABLE IF NOT EXISTS `member_users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码(BCrypt)',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(128) NOT NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像URL',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态: 0开启 1禁用',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_username_tenant_deleted` (`username`, `tenant_id`, `deleted`),
  UNIQUE KEY `uk_member_mobile_tenant_deleted` (`mobile`, `tenant_id`, `deleted`),
  UNIQUE KEY `uk_member_email_tenant_deleted` (`email`, `tenant_id`, `deleted`),
  KEY `idx_member_status` (`status`),
  KEY `idx_member_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='C端用户表';

-- 清理历史C端token，避免新旧账号来源混用。 / Clear legacy member tokens to avoid mixed user-source state.
DELETE FROM `system_oauth2_access_token` WHERE `user_type` = 2;
DELETE FROM `system_oauth2_refresh_token` WHERE `user_type` = 2;
