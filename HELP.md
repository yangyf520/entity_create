#使用Maven插件mybatis-generator生成代码配置

>在intelli IDEA 的菜单栏，点击RUN-Edit Configurations,
点击弹出窗口左上角的+号，新增Maven。

###新增Maven,然后在 “Command line” 选项中输入“mybatis-generator:generate -e”。
####这里加了“-e ”选项是为了让该插件输出详细信息，帮助我们定位问题。

[参照文档](https://www.jianshu.com/p/310c299846fc)

#table 元素说明
>此篇主要说明MyBatis Generator 配置文件详解 中的 table 元素，有关MyBatis Generator 配置文件详解的其他内容请移步：MyBatis Generator 配置文件详解
table 元素用来配置要通过内省的表。只有配置的才会生成实体类和其他文件。有一个必选属性(tableName)指定要生成的表名，可以使用SQL通配符匹配多个表。例如要生成全部的表，可以按如下配置：

<table tableName="%" />

##table 元素包含多个可选属性：
schema:数据库的schema,可以使用SQL通配符匹配。如果设置了该值，生成SQL的表名会变成如schema.tableName的形式。
catalog:数据库的catalog，如果设置了该值，生成SQL的表名会变成如catalog.tableName的形式。
alias:如果指定，这个值会用在生成的select查询SQL的表的别名和列名上。 列名会被别名为 alias_actualColumnName(别名_实际列名) 这种模式。
domainObjectName:生成对象的基本名称。如果没有指定，MBG会自动根据表名来生成名称。
enableXXX:XXX代表多种SQL方法，该属性用来指定是否生成对应的XXX语句。
selectByPrimaryKeyQueryId:DBA跟踪工具会用到，具体请看详细文档。
selectByExampleQueryId:DBA跟踪工具会用到，具体请看详细文档。
modelType:和<context>的defaultModelType含义一样，这里可以针对表进行配置，这里的配置会覆盖<context>的defaultModelType配置。
escapeWildcards:这个属性表示当查询列，是否对schema和表名中的SQL通配符 ('_' and '%') 进行转义。 对于某些驱动当schema或表名中包含SQL通配符时（例如，一个表名是MY_TABLE，有一些驱动需要将下划线进行转义）是必须的。默认值是false。
delimitIdentifiers:是否给标识符增加分隔符。默认false。当catalog,schema或tableName中包含空白时，默认为true。
delimitAllColumns:是否对所有列添加分隔符。默认false。

###该元素包含多个可用的<property>子元素，可选属性为：

constructorBased:和<javaModelGenerator>中的属性含义一样。
ignoreQualifiersAtRuntime:生成的SQL中的表名将不会包含schema和catalog前缀。
immutable:和<javaModelGenerator>中的属性含义一样。
modelOnly:此属性用于配置是否为表只生成实体类。如果设置为true就不会有Mapper接口。如果配置了<sqlMapGenerator>，并且modelOnly为true，那么XML映射文件中只有实体对象的映射元素(<resultMap>)。如果为true还会覆盖属性中的enableXXX方法，将不会生成任何CRUD方法。
rootClass:和<javaModelGenerator>中的属性含义一样。
rootInterface:和<javaClientGenerator>中的属性含义一样。
runtimeCatalog:运行时的catalog，当生成表和运行环境的表的catalog不一样的时候可以使用该属性进行配置。
runtimeSchema:运行时的schema，当生成表和运行环境的表的schema不一样的时候可以使用该属性进行配置。
runtimeTableName:运行时的tableName，当生成表和运行环境的表的tableName不一样的时候可以使用该属性进行配置。
selectAllOrderByClause:该属性值会追加到selectAll方法后的SQL中，会直接跟order by拼接后添加到SQL末尾。
useActualColumnNames:如果设置为true,那么MBG会使用从数据库元数据获取的列名作为生成的实体对象的属性。 如果为false(默认值)，MGB将会尝试将返回的名称转换为驼峰形式。 在这两种情况下，可以通过 元素显示指定，在这种情况下将会忽略这个（useActualColumnNames）属性。
useColumnIndexes:如果是true,MBG生成resultMaps的时候会使用列的索引,而不是结果中列名的顺序。
useCompoundPropertyNames:如果是true,那么MBG生成属性名的时候会将列名和列备注接起来. 这对于那些通过第四代语言自动生成列(例如:FLD22237),但是备注包含有用信息(例如:"customer id")的数据库来说很有用. 在这种情况下,MBG会生成属性名FLD2237_CustomerId。

###除了<property>子元素外，<table>还包含以下子元素：

<generatedKey> (0个或1个)
<columnRenamingRule> (0个或1个)
<columnOverride> (0个或多个)
<ignoreColumn> (0个或多个)

####后面的小节对这4个元素进行详细讲解。
generatedKey 元素
这个元素是可选的，最多可以配置一个。
这个元素用来指定自动生成主键的属性（identity字段或者sequences序列）。如果指定这个元素，MBG在生成insert的SQL映射文件中插入一个<selectKey>元素。 这个元素非常重要，这个元素包含下面两个必选属性：

column:生成列的列名。
sqlStatement:将返回新值的 SQL 语句。如果这是一个identity列，您可以使用其中一个预定义的的特殊值。预定义值如下：

JDBC:这会配置MBG使用MyBatis3支持的JDBC标准的生成key来生成代码。 这是一个独立于数据库获取标识列中的值的方法。 重要: 只有当目标运行为MyBatis3时才会产生正确的代码。 如果与iBATIS2一起使用目标运行时会产生运行时错误的代码。

这个元素还包含两个可选属性：

identity:当设置为true时,该列会被标记为identity列， 并且<selectKey>元素会被插入在insert后面。 当设置为false时，<selectKey>会插入到insert之前（通常是序列）。重要: 即使您type属性指定为post，您仍然需要为identity列将该参数设置为true。 这将标志MBG从插入列表中删除该列。默认值是false。
type:type=post and identity=true的时候生成的<selectKey>中的order=AFTER,当type=pre的时候，identity只能为false，生成的<selectKey>中的order=BEFORE。可以这么理解，自动增长的列只有插入到数据库后才能得到ID，所以是AFTER,使用序列时，只有先获取序列之后，才能插入数据库，所以是BEFORE。

columnRenamingRule 元素
该元素是可选的，最多可以配置一个，使用该元素可以在生成列之前，对列进行重命名。这对那些存在同一前缀的字段想在生成属性名时去除前缀的表非常有用。 例如假设一个表包含以下的列：

CUST_BUSINESS_NAME
CUST_STREET_ADDRESS
CUST_CITY
CUST_STATE

生成的所有属性名中如果都包含CUST的前缀可能会让人不爽。这些前缀可以通过如下方式定义重命名规则:
<columnRenamingRule searchString="^CUST_" replaceString="" />

>注意，在内部，MBG使用java.util.regex.Matcher.replaceAll方法实现这个功能。 请参阅有关该方法的文档和在Java中使用正则表达式的例子。
当<columnOverride>匹配一列时，这个元素（<columnRenamingRule>）会被忽略。<columnOverride>优先于重命名的规则。
该元素有一个必选属性（searchString）:定义将被替换的字符串的正则表达式。
该元素有一个可选属性（replaceString）:这是一个用来替换搜索字符串列每一个匹配项的字符串。如果没有指定，就会使用空字符串。
关于<table>的<property>属性useActualColumnNames对此的影响可以查看完整文档。
columnOverride 元素
该元素可选，可以配置多个。该元素将某些属性默认计算的值更改为指定的值。
该元素有一个必选属性（column）:要重写的列名。

####该元素有多个可选属性：
property:要使用的Java属性的名称。如果没有指定，MBG会根据列名生成。 例如，如果一个表的一列名为STRT_DTE，MBG会根据<table>的useActualColumnNames属性生成STRT_DTE或strtDte。
javaType:该列属性值为完全限定的Java类型。如果需要，这可以覆盖由JavaTypeResolver计算出的类型。 对某些数据库来说，这是必要的用来处理“奇怪的”数据库类型（例如MySql的unsigned bigint类型需要映射为java.lang.Object)。
jdbcType:该列的JDBC类型(INTEGER, DECIMAL, NUMERIC, VARCHAR等等)。 如果需要，这可以覆盖由JavaTypeResolver计算出的类型。 对某些数据库来说，这是必要的用来处理怪异的JDBC驱动 (例如DB2的LONGVARCHAR类型需要为iBATIS 映射为VARCHAR)。
typeHandler:用户定义的需要用来处理这列的类型处理器。它必须是一个继承iBATIS的TypeHandler类或TypeHandlerCallback接口（该接口很容易继承）的全限定的类名。如果没有指定或者是空白，iBATIS会用默认的类型处理器来处理类型。重要:MBG不会校验这个类型处理器是否存在或者可用。 MGB只是简单的将这个值插入到生成的SQL映射的配置文件中。
delimitedColumnName:指定是否应在生成的SQL的列名称上增加分隔符。 如果列的名称中包含空格，MGB会自动添加分隔符， 所以这个重写只有当列名需要强制为一个合适的名字或者列名是数据库中的保留字时是必要的。

####配置示例：
`<table schema="DB2ADMIN" tableName="ALLTYPES" >
  <columnOverride column="LONG_VARCHAR_FIELD" 
  javaType="java.lang.String" jdbcType="VARCHAR" />
</table>`

ignoreColumn 元素
该元素可选，可以配置多个。该元素可以用来屏蔽不需要生成的列。
该元素有一个必选属性（column）:要忽略的列名。
该元素还有一个可选属性（delimitedColumnName）:匹配列名的时候是否区分大小写。如果为true则区分。默认值为false，不区分大小写。
