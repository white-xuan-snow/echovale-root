create table album
(
    id           bigint unsigned auto_increment comment '专辑id'
        primary key,
    netease_id   bigint unsigned             null comment '网易云音乐id',
    name         varchar(60) charset utf8mb3 null comment '专辑名称',
    description  text                        null comment '描述',
    pic_url      text                        null comment '图片url',
    publish_time datetime                    null comment '发布时间',
    type         tinyint                     null comment '专辑类型',
    size         int                         null comment '专辑大小',
    sub_type     int                         null comment '副类型'
)
    comment '专辑';

create index album_name_index
    on album (name);

create index album_netease_id_index
    on album (netease_id);

create index album_publish_time_index
    on album (publish_time);

create table album_authors
(
    album_id  bigint unsigned not null comment '专辑id',
    author_id bigint unsigned not null comment '作者id',
    primary key (author_id, album_id)
)
    comment '专辑作者关联表';

create index album_authors_album_id_index
    on album_authors (album_id);

create index album_authors_author_id_index
    on album_authors (author_id);

create table author
(
    id          bigint unsigned auto_increment comment 'id'
        primary key,
    netease_id  bigint unsigned             null comment '网易云音乐作者id',
    name        varchar(60) charset utf8mb3 null comment '名称',
    trans_names text                        null comment '中文译名列表',
    alias       text                        null comment '别名列表',
    music_size  int                         null comment '发布音乐数量',
    album_size  int                         null comment '专辑数量',
    mv_size     int                         null comment 'mv数量',
    cover_url   text                        null comment '封面url',
    avatar_url  text                        null comment '头像url',
    description text                        null comment '简介',
    identify    text                        null comment '类型(歌手，编曲。。。)'
)
    comment '作者';

create index author_alias_index
    on author (alias(60));

create index author_name_index
    on author (name);

create index author_trans_names_index
    on author (trans_names(60));

create table language
(
    id   int unsigned auto_increment comment '语种id'
        primary key,
    name tinytext null comment '语种名称'
)
    comment '语种';

create table lyric
(
    id             bigint unsigned auto_increment comment '歌词id'
        primary key,
    netease_lrc    tinytext null comment '网易云音乐lrc歌词路径',
    netease_tlrc   tinytext null comment '网易云音乐翻译歌词路径',
    netease_yrc    tinytext null comment '网易云音乐逐字歌词路径',
    netese_romalrc tinytext null comment '网易云音乐罗马音歌词路径',
    netease_klrc   tinytext null comment '网易云未知歌词',
    amll_ttml      tinytext null comment 'amll ttml歌词url',
    echo_ttml      tinytext null comment '回声ttml歌词路径'
)
    comment '歌词';

create table music
(
    id         bigint unsigned auto_increment comment '音乐id'
        primary key,
    netease_id bigint unsigned             null comment '网易云音乐id',
    name       varchar(60) charset utf8mb3 null comment '音乐名称',
    album_id   bigint unsigned             null comment '专辑id',
    fee        tinyint                     null comment '付费等级 0免费无版权 1VIP歌曲 4购买专辑 8非会员可播放最低音质 ',
    cover_type tinyint                     null comment '0未知 1原唱 2翻唱',
    mv_id      bigint unsigned             null comment 'mv的id 0表示没有id',
    time       int unsigned                null comment '持续时间(单位：毫秒)',
    chorus     tinytext                    null comment '副歌时间'
)
    comment '音乐';

create index music_album_id_index
    on music (album_id);

create index music_name_index
    on music (name(30));

create index music_netease_id_index
    on music (netease_id);

create table music_authors
(
    author_id bigint unsigned not null comment '作者id',
    music_id  bigint unsigned not null comment '音乐id',
    primary key (music_id, author_id)
)
    comment '音乐作者关联表';

create index music_authors_author_id_index
    on music_authors (author_id);

create index music_authors_music_id_index
    on music_authors (music_id);

create table music_awards
(
    id       bigint unsigned auto_increment comment '获奖id'
        primary key,
    music_id bigint unsigned null comment '音乐id',
    content  tinytext        null comment '获奖内容'
)
    comment '音乐获奖关联表';

create index music_awards_content_index
    on music_awards (content(60));

create index music_awards_music_id_index
    on music_awards (music_id);

create table music_entertainment
(
    id       bigint unsigned auto_increment comment '影视作品id'
        primary key,
    music_id bigint unsigned null comment '音乐id',
    content  tinytext        null comment '影视作品名称'
)
    comment '音乐影视作品关联表';

create index music_entertainment_content_index
    on music_entertainment (content(60));

create index music_entertainment_music_id_index
    on music_entertainment (music_id);

create table music_info_ext
(
    music_id     bigint unsigned not null comment '音乐id'
        primary key,
    publish_time datetime        null comment '发行时间',
    no           int unsigned    null comment '专辑排序 0表示没有排序',
    bpm          int unsigned    null comment '音乐bpm'
)
    comment '音乐信息扩展表';

create table music_languages
(
    music_id    bigint unsigned not null comment '音乐id',
    language_id int unsigned    not null comment '语种id',
    primary key (music_id, language_id)
)
    comment '音乐语种关联表';

create table music_qualities
(
    id       bigint unsigned auto_increment comment '品质id'
        primary key,
    music_id bigint unsigned  null comment '音乐id',
    level    tinyint unsigned null comment '品质等级',
    br       int unsigned     null comment '码率',
    sr       int unsigned     null comment '采样率'
)
    comment '音乐品质关联表';

create table music_sheets
(
    id       bigint unsigned auto_increment comment '乐谱id'
        primary key,
    music_id bigint unsigned not null comment '音乐id',
    name     tinytext        null comment '乐谱名称',
    url      text            null comment '图片url'
)
    comment '音乐乐谱表关联表';

create table music_styles
(
    music_id bigint unsigned not null comment '音乐id',
    style_id bigint unsigned not null comment '曲风id',
    primary key (music_id, style_id)
)
    comment '音乐曲风关联表';

create table music_tags
(
    music_id bigint unsigned not null comment '音乐id',
    tag_id   bigint unsigned not null comment '标签id',
    primary key (music_id, tag_id)
)
    comment '音乐标签关联表';

create table permission
(
    id          int unsigned auto_increment comment '权限id'
        primary key,
    code        tinyint unsigned null comment '权限代码',
    description text             null comment '描述',
    department  tinyint unsigned null comment '所属部门'
)
    comment '权限';

create table playlist
(
    id          bigint unsigned auto_increment comment '播放列表id'
        primary key,
    netease_id  bigint unsigned              null comment '网易云歌单id',
    cover_url   tinytext                     null comment '封面url',
    name        varchar(120) charset utf8mb3 null comment '歌单名称',
    description text                         null comment '描述',
    update_time datetime                     null comment '更新时间',
    create_time datetime                     null comment '创建时间',
    tags        tinytext                     null comment '标签列表',
    is_user     tinyint(1)                   null comment '是不是用户创建的歌单'
)
    comment '播放列表';

create index playlist_create_time_index
    on playlist (create_time);

create index playlist_name_index
    on playlist (name);

create index playlist_netease_id_index
    on playlist (netease_id);

create index playlist_tags_index
    on playlist (tags(60));

create index playlist_update_time_index
    on playlist (update_time);

create table playlist_musics
(
    playlist_id bigint unsigned not null comment '歌单id',
    music_id    bigint unsigned not null comment '音乐id',
    primary key (music_id, playlist_id)
)
    comment '歌单音乐关联表';

create index playlist_musics_music_id_index
    on playlist_musics (music_id);

create index playlist_musics_playlist_id_index
    on playlist_musics (playlist_id);

create index playlist_musics_playlist_id_index_2
    on playlist_musics (playlist_id);

create table role
(
    id          int unsigned auto_increment comment '角色id'
        primary key,
    name        varchar(60) charset utf8mb3 null comment '角色名称',
    description text                        null comment '描述',
    status      tinyint(1)                  null comment '状态'
)
    comment '角色';

create table role_permissions
(
    role_id       int unsigned not null comment '角色id',
    permission_id int unsigned not null comment '权限id',
    primary key (permission_id, role_id)
)
    comment '用户权限关联表';

create table style
(
    id   bigint unsigned not null comment '曲风或标签id'
        primary key,
    name tinytext        null comment '曲风名称'
)
    comment '曲风';

create table tag
(
    id   bigint unsigned not null comment '标签id'
        primary key,
    name tinytext        null comment '标签名称'
)
    comment '标签';

create table toplist
(
    id           int unsigned auto_increment comment '排行榜id'
        primary key,
    netease_id   bigint unsigned             null comment '网易云id',
    name         varchar(60) charset utf8mb3 null comment '排行榜名称',
    description  text                        null comment '描述',
    create_time  datetime                    null comment '创建时间',
    update_time  datetime                    null comment '更新时间',
    cover_url    tinytext                    null comment '封面图片url',
    ordered      tinyint(1)                  null comment '是否排序',
    high_quality tinyint(1)                  null comment '是否为精选榜单',
    update_desc  tinytext                    null comment '更新时间描述'
)
    comment '排行榜';

create index toplist_create_time_index
    on toplist (create_time);

create index toplist_name_index
    on toplist (name);

create index toplist_netease_id_index
    on toplist (netease_id);

create index toplist_update_time_index
    on toplist (update_time);

create table toplist_musics
(
    toplist_id int unsigned    not null comment '排行榜id',
    music_id   bigint unsigned not null comment '音乐id',
    primary key (toplist_id, music_id)
)
    comment '排行榜音乐关联表';

create index toplist_musics_music_id_index
    on toplist_musics (music_id);

create index toplist_musics_toplist_id_index
    on toplist_musics (toplist_id);

create table user
(
    id               bigint unsigned auto_increment comment '用户id'
        primary key,
    username         varchar(60) charset utf8mb3 null comment '用户名',
    password         varchar(128)                null comment '密码',
    emai             varchar(60)                 null comment '邮箱',
    phone            char(11)                    null comment '手机号',
    status           tinyint(1)                  null comment '状态',
    user_meta_ext_id bigint unsigned             null comment '用户元数据扩展表id',
    user_info_ext_id bigint unsigned             null comment '用户信息扩展表id'
)
    comment '用户';

create index user_user_info_ext_id_index
    on user (user_info_ext_id);

create index user_user_meta_ext_id_index
    on user (user_meta_ext_id);

create index user_username_index
    on user (username);

create table user_info_ext
(
    id          bigint unsigned auto_increment comment '用户信息扩展表id'
        primary key,
    avatar_path tinytext    null comment '头像alist地址',
    ip_addr     varchar(30) null comment 'ip登录地址(指定ip不加密, 其余ip加密)',
    login_time  datetime    null comment '登录时间',
    create_time datetime    null comment '创建时间',
    is_online   tinyint(1)  null comment '是否在线'
)
    comment '用户信息扩展表';

create table user_meta_ext
(
    id          bigint unsigned auto_increment comment '元数据扩展表id'
        primary key,
    settings_id bigint unsigned null comment '用户设置关联表id',
    likelist_id bigint unsigned null comment '用户喜欢列表关联表id'
)
    comment '用户元数据扩展表';

create index user_meta_ext_likelist_id_index
    on user_meta_ext (likelist_id);

create index user_meta_ext_settings_id_index
    on user_meta_ext (settings_id);

create table user_musics
(
    user_id   bigint unsigned not null comment '用户id',
    music_id  bigint unsigned not null comment '音乐id',
    play_time datetime        null comment '上一次播放时间',
    times     int unsigned    null comment '播放次数',
    primary key (music_id, user_id)
)
    comment '用户音乐关联表';

create index user_musics_music_id_index
    on user_musics (music_id);

create index user_musics_user_id_index
    on user_musics (user_id);

create table user_playlists
(
    user_id     bigint unsigned not null comment '用户id',
    playlist_id bigint unsigned not null comment '歌单id',
    primary key (user_id, playlist_id)
)
    comment '用户歌单关联表';

create index user_playlists_playlist_id_index
    on user_playlists (playlist_id);

create index user_playlists_user_id_index
    on user_playlists (user_id);

create table user_roles
(
    user_id bigint unsigned not null comment '用户id',
    role_id int unsigned    not null comment '角色id',
    primary key (user_id, role_id)
)
    comment '用户权限关联表';

