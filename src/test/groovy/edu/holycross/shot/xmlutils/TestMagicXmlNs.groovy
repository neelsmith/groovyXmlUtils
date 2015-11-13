package edu.holycross.shot.xmlutils

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestMagicXmlNs {

  @Test
  void testMagicNode() {
    String xmlSrc = """
    <div xmlns="http://www.tei-c.org/ns/1.0">
      <l>Sing, <w>god<unclear>dess</unclear>
          </w></l>
     </div>
    """
    XmlNode n  = new XmlNode(xmlSrc)
    def noMagic = n.collectText().split(/\s/)
    assert noMagic.size() == 3
    assert noMagic[1] == "god"

    //define a magic node tokenizing on "w" element in TEI namespace:
    n.setMagic("http://www.tei-c.org/ns/1.0","w","","")
    assert n.magicMarkup == TokenizingMarkup.ELEMENT_ONLY
    def collected = n.collectText()
    def wMagic = collected.split(/\s/)
    assert wMagic.size() == 2
    assert wMagic[1] == "goddess"
  }



}
