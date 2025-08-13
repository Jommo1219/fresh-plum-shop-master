create table if not exists admin
(
    id          bigint                               not null comment 'ID'
        primary key,
    username    varchar(20)                          not null comment '用户名',
    password    varchar(32)                          not null comment '密码',
    phone       char(11)                             null comment '手机号',
    email       varchar(64)                          null comment '邮箱',
    avatar      varchar(255)                         null comment '头像URL',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint(1) default 0                 null comment '逻辑删除',
    status      tinyint(1) default 1                 null comment '账号启用状态：0->禁用；1->启用',
    nickname    varchar(10)                          null comment '昵称',
    constraint idx_email
        unique (email),
    constraint idx_phone
        unique (phone),
    constraint idx_username
        unique (username)
)
    comment '管理员表';

create table if not exists goods_attribute
(
    id                          bigint                               not null comment '属性ID'
        primary key,
    goods_attribute_category_id bigint                               not null comment '商品分类ID',
    name                        varchar(64)                          not null comment '商品属性名称',
    input_list                  varchar(255)                         not null comment '可选值列表，以逗号隔开',
    create_time                 datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time                 datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted                  tinyint(1) default 0                 null comment '逻辑删除',
    sort                        int        default 0                 null comment '排序值',
    constraint unique_name
        unique (goods_attribute_category_id, name)
)
    comment '商品属性表';

create table if not exists goods_attribute_category
(
    id              bigint                               not null comment '属性类型ID'
        primary key,
    name            varchar(64)                          not null comment '属性类型名',
    attribute_count int        default 0                 null comment '属性数量',
    create_time     datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted      tinyint(1) default 0                 null comment '逻辑删除'
)
    comment '产品属性分类表';

create table if not exists goods_category
(
    id          bigint                               not null comment '分类ID'
        primary key,
    parent_id   bigint     default 0                 null comment '父分类ID',
    name        varchar(64)                          not null comment '分类名称',
    level       tinyint(1)                           not null comment '分类层级',
    sort        int        default 0                 null comment '排序值',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint(1) default 0                 null comment '逻辑删除',
    pic         varchar(255)                         null comment '分类图片'
);

create table if not exists goods
(
    id                          bigint                                   not null comment '商品ID'
        primary key,
    goods_category_id           bigint                                   not null comment '商品分类ID',
    goods_attribute_category_id bigint                                   not null comment '商品属性类型ID',
    name                        varchar(128)                             not null comment '商品名称',
    pic                         varchar(255)                             null comment '商品主图',
    album_pics                  varchar(1000)                            null comment '画册图片用逗号分割',
    goods_sn                    varchar(64)                              null comment '货号',
    publish_status              tinyint(1)     default 0                 null comment '上架状态',
    sort                        int            default 0                 null comment '排序',
    sale                        int            default 0                 null comment '销量',
    price                       decimal(10, 2)                           not null comment '价格',
    original_price              decimal(10, 2) default 0.00              null comment '市场价',
    sub_title                   varchar(255)                             null comment '副标题',
    description                 text                                     null comment '商品描述',
    stock                       int            default 0                 null comment '库存',
    unit                        varchar(16)                              null comment '单位',
    create_time                 datetime       default CURRENT_TIMESTAMP null comment '创建时间',
    update_time                 datetime       default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted                  tinyint(1)     default 0                 null comment '逻辑删除',
    constraint goods_ibfk_1
        foreign key (goods_category_id) references goods_category (id),
    constraint goods_ibfk_2
        foreign key (goods_attribute_category_id) references goods_attribute_category (id)
);

create index goods_attribute_category_id
    on goods (goods_attribute_category_id);

create index goods_category_id
    on goods (goods_category_id);

create index idx_name
    on goods (name);

create table if not exists goods_attribute_value
(
    id                 bigint      not null comment 'ID'
        primary key,
    goods_id           bigint      not null comment '商品ID',
    goods_attribute_id bigint      not null comment '商品属性ID',
    value              varchar(64) not null comment '参数的值',
    constraint goods_attribute_value_ibfk_1
        foreign key (goods_id) references goods (id),
    constraint goods_attribute_value_ibfk_2
        foreign key (goods_attribute_id) references goods_attribute (id)
)
    comment '商品属性值表';

create index goods_attribute_id
    on goods_attribute_value (goods_attribute_id);

create index goods_id
    on goods_attribute_value (goods_id);

create table if not exists home_advertise
(
    id     bigint               not null comment 'ID'
        primary key,
    name   varchar(100)         null comment '广告名称',
    pic    varchar(500)         null comment '图片地址',
    status tinyint(1) default 0 null comment '上下线状态：0->下线；1->上线',
    note   varchar(500)         null comment '备注'
)
    comment '首页广告表';

create table if not exists member
(
    id          bigint                               not null comment 'ID'
        primary key,
    username    varchar(20)                          not null comment '用户名',
    password    varchar(32)                          not null comment '密码',
    nickname    varchar(20)                          null comment '昵称',
    phone       char(11)                             not null comment '手机号码',
    avatar      varchar(255)                         null comment '头像',
    gender      tinyint(1) default 0                 null comment '性别：0->未知；1->男；2->女',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  tinyint(1) default 0                 null comment '逻辑删除',
    constraint idx_phone
        unique (phone),
    constraint idx_username
        unique (username)
);

create table if not exists member_receive_address
(
    id             bigint       not null comment 'ID'
        primary key,
    member_id      bigint       not null comment '用户ID',
    name           varchar(100) null comment '收货人名称',
    phone          char(11)     null comment '收货人联系方式',
    default_status tinyint(1)   null comment '是否为默认',
    post_code      varchar(100) null comment '邮政编码',
    province       varchar(100) null comment '省份/直辖市',
    city           varchar(100) null comment '城市',
    region         varchar(100) null comment '区',
    detail_address varchar(128) null comment '详细地址',
    region_code    char(6)      null comment '用于前端地区选择显示',
    constraint member_receive_address_ibfk_1
        foreign key (member_id) references member (id)
);

create index member_id
    on member_receive_address (member_id);

create table if not exists menu
(
    id          bigint                             not null
        primary key,
    parent_id   bigint                             not null comment '父级ID',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    title       varchar(100)                       null comment '菜单名称',
    level       int                                null comment '菜单级数',
    sort        int                                null comment '菜单排序',
    name        varchar(100)                       null comment '前端名称',
    icon        varchar(200)                       null comment '前端图标',
    is_hidden   tinyint                            null comment '前端隐藏'
)
    comment '后台菜单表';

create table if not exists `order`
(
    id                      bigint                                   not null comment 'ID'
        primary key,
    member_id               bigint                                   not null comment '用户ID',
    order_sn                varchar(64)                              null comment '订单编号',
    create_time             datetime       default CURRENT_TIMESTAMP null comment '创建时间',
    member_username         varchar(64)                              null comment '用户账号',
    total_amount            decimal(10, 2)                           null comment '订单总金额',
    pay_amount              decimal(10, 2)                           null comment '实际支付金额',
    pay_type                tinyint(1)     default 0                 null comment '支付方式（0->未支付；1->微信支付；2->支付宝支付）',
    status                  int                                      null comment '订单状态：0->待付款;1->待发货;2->已发货;3->已完成;4->已关闭;5->无效订单;',
    receiver_name           varchar(100)                             null comment '收货人姓名',
    receiver_phone          char(11)                                 null comment '收货人电话',
    receiver_post_code      char(6)                                  null comment '收货人邮编',
    receiver_province       varchar(32)                              null comment '省份/直辖市',
    receiver_city           varchar(32)                              null comment '城市',
    receiver_region         varchar(32)                              null comment '区',
    receiver_detail_address varchar(200)                             null comment '详细地址',
    note                    varchar(500)                             null comment '订单备注',
    confirm_status          tinyint(1)                               null comment '确认收货状态：0->未确认；1->已确认',
    is_deleted              tinyint(1)                               null comment '删除状态：0->未删除；1->已删除',
    payment_time            datetime                                 null comment '支付时间',
    delivery_time           datetime                                 null comment '发货时间',
    receive_time            datetime                                 null comment '确认收货时间',
    update_time             datetime       default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    freight_amount          decimal(10, 2) default 0.00              null comment '运费金额',
    delivery_company        varchar(64)                              null comment '物流公司',
    delivery_sn             varchar(64)                              null comment '物流单号',
    constraint order_member_id_fk
        foreign key (member_id) references member (id)
);

create table if not exists order_operate_history
(
    id           bigint                             not null comment 'ID'
        primary key,
    order_id     bigint                             not null comment '订单id',
    operate_man  varchar(100)                       null comment '操作人：用户；后台管理员',
    create_time  datetime default CURRENT_TIMESTAMP null comment '操作时间',
    order_status tinyint(1)                         null comment '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    note         varchar(500)                       null comment '备注',
    constraint order_operate_history_order_id_fk
        foreign key (order_id) references `order` (id)
)
    comment '订单操作历史表';

create table if not exists order_return_reason
(
    id          bigint                               not null comment 'ID'
        primary key,
    name        varchar(100)                         null comment '退货原因',
    sort        int        default 0                 null comment '排序',
    status      tinyint(1) default 1                 null comment '状态：0->不启用；1->启用',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint idx_name
        unique (name)
);

create table if not exists role
(
    id          bigint                               not null comment 'ID'
        primary key,
    name        varchar(20)                          not null comment '名称',
    description varchar(500)                         null comment '描述',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    status      tinyint(1) default 1                 null comment '启用状态：0->禁用；1->启用',
    sort        int        default 0                 null comment '排序',
    constraint idx_name
        unique (name)
)
    comment '后台用户角色表';

create table if not exists admin_role_relation
(
    id       bigint not null comment 'ID'
        primary key,
    admin_id bigint not null,
    role_id  bigint not null,
    constraint admin_role_relation_ibfk_1
        foreign key (admin_id) references admin (id),
    constraint admin_role_relation_ibfk_2
        foreign key (role_id) references role (id)
)
    comment '后台用户和角色关系表';

create index admin_id
    on admin_role_relation (admin_id);

create index role_id
    on admin_role_relation (role_id);

create table if not exists role_menu_relation
(
    id      bigint not null
        primary key,
    role_id bigint not null comment '角色id',
    menu_id bigint not null comment '菜单ID',
    constraint role_menu_relation_menu_id_fk
        foreign key (menu_id) references menu (id),
    constraint role_menu_relation_role_id_fk
        foreign key (role_id) references role (id)
)
    comment '角色菜单关系表';

create table if not exists sku_stock
(
    id         bigint         not null comment 'ID'
        primary key,
    goods_id   bigint         not null comment '商品ID',
    sku_code   varchar(64)    not null comment 'sku编码',
    price      decimal(10, 2) not null comment '价格',
    stock      int default 0  not null comment '库存',
    pic        varchar(255)   null comment '展示图片',
    sale       int default 0  null comment '销量',
    sp_data    varchar(500)   null comment '商品销售属性',
    lock_stock int default 0  null comment '锁定库存',
    constraint sku_stock_ibfk_1
        foreign key (goods_id) references goods (id)
);

create table if not exists cart_item
(
    id                bigint                               not null
        primary key,
    goods_id          bigint                               not null,
    goods_sku_id      bigint                               not null,
    member_id         bigint                               not null,
    member_nickname   varchar(500)                         null comment '用户昵称',
    quantity          int        default 1                 null comment '购买数量',
    price             decimal(10, 2)                       null comment '商品价格',
    goods_pic         varchar(1000)                        null comment '商品主图',
    goods_name        varchar(500)                         null comment '商品名称',
    goods_sub_title   varchar(500)                         null comment '商品副标题',
    goods_sku_code    varchar(200)                         null comment '商品sku条码',
    create_time       datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time       datetime   default (now())           null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted        tinyint(1) default 0                 null comment '是否删除',
    goods_category_id bigint                               not null comment '商品分类',
    goods_sn          varchar(200)                         null,
    goods_attr        varchar(500)                         null comment '商品规格',
    constraint cart_item_ibfk_1
        foreign key (goods_id) references goods (id),
    constraint cart_item_ibfk_2
        foreign key (goods_sku_id) references sku_stock (id),
    constraint cart_item_ibfk_3
        foreign key (member_id) references member (id),
    constraint cart_item_ibfk_4
        foreign key (goods_category_id) references goods_category (id)
);

create index goods_category_id
    on cart_item (goods_category_id);

create index goods_id
    on cart_item (goods_id);

create index goods_sku_id
    on cart_item (goods_sku_id);

create index member_id
    on cart_item (member_id);

create table if not exists order_item
(
    id                bigint         not null comment 'ID'
        primary key,
    order_id          bigint         not null comment '订单id',
    order_sn          varchar(64)    null comment '订单编号',
    goods_id          bigint         not null comment '商品id',
    goods_pic         varchar(500)   null comment '商品图片',
    goods_name        varchar(200)   null comment '商品名称',
    goods_sn          varchar(64)    null comment '商品编号',
    goods_price       decimal(10, 2) null comment '销售价格',
    goods_quantity    int            null comment ' 购买数量',
    goods_sku_id      bigint         not null comment '商品sku编号',
    goods_sku_code    varchar(50)    null comment '商品sku条码',
    goods_category_id bigint         not null comment '商品分类id',
    total_amount      decimal(10, 2) null comment '商品总价',
    goods_attr        varchar(500)   null comment '商品规格',
    constraint order_item_goods_category_id_fk
        foreign key (goods_category_id) references goods_category (id),
    constraint order_item_goods_id_fk
        foreign key (goods_id) references goods (id),
    constraint order_item_order_id_fk
        foreign key (order_id) references `order` (id),
    constraint order_item_sku_stock_id_fk
        foreign key (goods_sku_id) references sku_stock (id)
)
    comment '订单中所包含的商品';

create index goods_id
    on sku_stock (goods_id);

