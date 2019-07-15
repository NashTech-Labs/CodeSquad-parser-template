Commonly used methods of the Elem class

Method                 Description
------                 -----------

x \ "div"              Searches the XML literal x for elements of type <div>.
                       Only searches immediate child nodes (no grandchild or “descendant” nodes).

x \\ "div"             Searches the XML literal x for elements of type <div>. Returns matching
                       elements from child nodes at any depth of the XML tree.

x.text                 Returns a concatenation of text(n) for each child n.

x.toString             Emits the XML literal as a String.
                       Use scala.xml.PrettyPrinter to format the output, if desired.
x \\ "div" \ "@attribute"  Return the attribute value from div tag.


Example:

scala> val document  = <languages>
<language>Scala</language>
<language>Java</language>
<language>C++</language>
<language>Kotlin</language>
</languages>

scala> (document \ "language")
res0: scala.xml.NodeSeq = NodeSeq(<language>Scala</language>, <language>Java</language>, <language>C++</language>, <language>Kotlin</language>)

scala> (document \ "language").foreach(doc => println(doc.text))
Scala
Java
C++
Kotlin

scala> val doc = <div class="content"><p name="paragraph"><span name="attribute value">Hi Span</span>Hello</p><p>world</p></div>
doc: scala.xml.Elem = <div class="content"><p name="paragraph"><span name="attribute value">Hi Span</span>Hello</p><p>world</p></div

scala> (doc \ "span")
res10: scala.xml.NodeSeq = NodeSeq()

scala> (doc \ "span").text
res11: String = ""

scala> (doc \\ "span")
res9: scala.xml.NodeSeq = NodeSeq(<span name="attribute value">Hi Span</span>)

scala> (doc \\ "span" \ "@name")
res12: scala.xml.NodeSeq = attribute value

scala> (doc \\ "span" \ "@nope")
res13: scala.xml.NodeSeq = NodeSeq()
