## mapstruct使用文档

### Maven依赖安装

#### Dependency依赖配置

```xml
  <!-- mapstruct -->
  <dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${mapstruct.version}</version>
  </dependency>

  <dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-jdk8</artifactId>
    <version>${mapstruct.version}</version>
  </dependency>

  <dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>${mapstruct.version}</version>
    <scope>provided</scope>
  </dependency>
```

#### Maven Compiler Plugin配置

```xml
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>${maven-compiler-plugin.version}</version>
    <configuration>
      <compilerArgs>
        <arg>-Amapstruct.suppressGeneratorVersion</arg>
      </compilerArgs>
      <source>${java.version}</source>
      <target>${java.version}</target>
      <release>${java.version}</release>
      <annotationProcessorPaths>
        <path>
          <groupId>org.mapstruct</groupId>
          <artifactId>mapstruct-processor</artifactId>
          <version>${mapstruct.version}</version>
        </path>
      </annotationProcessorPaths>
    </configuration>
  </plugin>
```

### Idea插件安装

在Marketplace搜索MapStruct Support进行安装

主要用于mapstruct部分注解中字符串的提示

### 快速入门

#### 场景介绍

MusicDTO -> MusicPO

MusicDTO

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicDTO {
    private Long id;
    private Long neteaseId;
    private String name;
    private Integer fee;
    private Integer coverType;
    private Long mvId;
    private Integer time;
    private String chorus;
    @TableField(exist = false)
    private AlbumPO album;
    @TableField(exist = false)
    private List<AuthorPO> authors;
    @TableField(exist = false)
    private List<MusicAwardsPO> awards;
    @TableField(exist = false)
    private LyricPO lyric;
    @TableField(exist = false)
    private MusicInfoExtendPO info;
    @TableField(exist = false)
    private List<MusicQualitiesPO> qualities;
    @TableField(exist = false)
    private List<MusicEntertainmentPO> entertainments;
    @TableField(exist = false)
    private List<LanguagePO> languagePOS;
    @TableField(exist = false)
    private List<MusicStylesPO> styles;
    @TableField(exist = false)
    private List<MusicTagsPO> tags;
    @TableField(exist = false)
    private List<MusicSheetsPO> sheets;
}
```

MusicPO

```java
@Data
@Builder
@TableName("music")
public class MusicPO {
    @TableId
    private Long id;
    private Long neteaseId;
    private String name;
    private Long albumId;
    private Integer fee;
    private Integer coverType;
    private Long mvId;
    private Integer time;
    private String chorus;
}
```

AlbumPO

```java
@Data
@Builder
@TableName("album")
public class AlbumPO {
    @TableId
    private Long id;
    private String name;
    private Long neteaseId;
    private String description;
    private String picUrl;
    private Long publishTime;
    private Integer type;
    private Integer size;
    private Integer subType;
}
```

有相同名称的字段，比如：id、name.

有子级字段的映射，比如：album(AlbumPO).id -> albumId.

#### 基础用法

```java
@Mapper(config = MappingConfig.class,
        componentModel = "spring")
public interface MusicPOMapping {

    @Mapping(source = "dto.album.id", target = "albumId")
    MusicPO modelToPO(MusicDTO dto);

    @Mapping(source = "dto.album.id", target = "albumId")
    MusicPO modelToPO(MusicDTO dto, @MappingTarget MusicPO po);
}
```

其中@Mapper为mapstruct的接口注解

config指定了映射接口的映射配置项

componentModel = ”spring“ 指定了编译生成的实现类xxxxImpl接受Spring IoC容器的管理

mapstruct会自动映射名称相同的同级字段

@Mapping注解进行字段的自定义映射

@Mapping注解通常接受两个参数，source与target，指定映射源与映射对象。对于深层次的字段映射，使用.进行层次深入

@MappingTarget指定映射对象

#### 自定义映射方法



