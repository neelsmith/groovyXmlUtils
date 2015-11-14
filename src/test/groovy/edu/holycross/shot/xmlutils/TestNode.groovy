package edu.holycross.shot.xmlutils

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestNode {


  @Test
  void testNoNamespace() {
    String xmlSrc = """
    <div n="1">
		<l n="1">Sing goddess, the rage of <persName n="urn:cite:hmt:pers.pers1">Achilles</persName>
		</l>
    <l n="2">destructive rage</l>
    </div>
    """
    XmlNode n  = new XmlNode(xmlSrc)
    Integer expectedWords = 8
    String expectedFirst = "Sing"
    String nodeTxt = n.collectText()
    def asciiWordList =  nodeTxt.toString().split(/\s/)
    assert expectedWords == asciiWordList.size()
    assert asciiWordList[0] == expectedFirst


  }

  @Test
  void testBadXml() {
    String xmlSrc = "<open>Text context."
    assert shouldFail {
      XmlNode n  = new XmlNode(xmlSrc)
    }
  }
}
