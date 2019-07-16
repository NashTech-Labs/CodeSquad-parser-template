### Commonly used methods of the Elem class

``` sh
Method                       Description
------                       -----------

x \ "div"                    Searches the XML literal x for elements of type <div>.
                             Only searches immediate child nodes (no grandchild or “descendant” nodes).

x \\ "div"                  Searches the XML literal x for elements of type <div>. Returns matching
                            elements from child nodes at any depth of the XML tree.

x.text                      Returns a concatenation of text(n) for each child n.

x.toString                   Emits the XML literal as a String.
                             Use scala.xml.PrettyPrinter to format the output, if desired.

x \\ "div" \ "@attribute"   When you need to extract tag attributes, place an @ character before the attribute name.
```

#### The internal implementation of text and toString methods

```
  override def toString(): String = theSeq.mkString
  def text: String = (this map (_.text)).mkString
  def mkString: String = mkString("")
```

### Example:
```
scala> val document  = <languages>
<language>Scala</language>
<language>Java</language>
<language>C++</language>
<language>Kotlin</language>
</languages>
```
#### Fetch all languages from XML:
```
scala> (document \ "language")
res0: scala.xml.NodeSeq = NodeSeq(<language>Scala</language>, <language>Java</language>, <language>C++</language>, <language>Kotlin</language>)

scala> (document \ "language").text
res1: String = ScalaJavaC++Kotlin

scala> (document \ "language").map(_.text)
res2: scala.collection.immutable.Seq[String] = List(Scala, Java, C++, Kotlin)
```

### If tag is missing then it will return an empty NodeSeq and List
```
scala> val document  = <languages></languages>
document: scala.xml.Elem = <languages></languages>

scala> (document \ "language")
res1: scala.xml.NodeSeq = NodeSeq()

scala> (document \ "language").text
res2: String = ""

scala> (document \ "language").map(_.text)
res3: scala.collection.immutable.Seq[String] = List()

```


### Get value from attribute and tag
```
val weather =
<rss>
  <channel>
    <title>Yahoo! Weather - Boulder, CO</title>
    <item>
     <title>Conditions for Boulder, CO at 2:54 pm MST</title>
     <forecast day="Thu" date="10 Nov 2011" low="37" high="58" text="Partly Cloudy"
               code="29" />
    </item>
  </channel>
</rss>
```
### You can also directly access the attributes of the <forecast> element with these expressions. Each attributes is retured as a NodeSeq:
```
scala> val day = weather \ "channel" \ "item" \ "forecast" \ "@day"
day: scala.xml.NodeSeq = Thu

scala> val date = weather \ "channel" \ "item" \ "forecast" \ "@date"
date: scala.xml.NodeSeq = 10 Nov 2011

```
### You can convert that to a String with the text method and toString method:

```
scala> val date = (weather \ "channel" \ "item" \ "forecast" \ "@day").text
date: String = Thu

scala> val date = (weather \ "channel" \ "item" \ "forecast" \ "@date").toString
date: String = 10 Nov 2011
```

### With the help of \\\ , you can directly access title tag value and the forecast tag and its attribute value.

```
scala> val titles = (weather \\ "title")
titles: scala.xml.NodeSeq = NodeSeq(<title>Yahoo! Weather - Boulder, CO</title>, <title>Conditions for Boulder, CO at 2:54 pm MST</title>)

scala> val titles = (weather \\ "title").map(_.text)
titles: scala.collection.immutable.Seq[String] = List(Yahoo! Weather - Boulder, CO, Conditions for Boulder, CO at 2:54 pm MST)

scala> val forecast = (weather \\ "forecast")
forecast: scala.xml.NodeSeq = NodeSeq(<forecast day="Thu" date="10 Nov 2011" low="37" high="58" text="Partly Cloudy" code="29"/>)

scala> val date = (forecast \ "@date").toString
date: String = 10 Nov 2011

scala> val day = (forecast \ "@day").text
day: String = Thu
```

##### References: [https://alvinalexander.com/scala/xml-parsing-xpath-extract-xml-tag-attributes](https://alvinalexander.com/scala/xml-parsing-xpath-extract-xml-tag-attributes)
