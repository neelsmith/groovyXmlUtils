package edu.holycross.shot.xmlutils

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestMagic {

  @Test
  void testMagicConfig() {

    String xmlSrc = """
    <l>Sing, <w>god<unclear>dess</unclear>
    </w></l>
    """
    XmlNode n  = new XmlNode(xmlSrc)
    def noMagic = n.collectText().split(/\s/)
    assert noMagic.size() == 3
    assert noMagic[1] == "god"

    //define a magic node tokenizing on "w" element:
    n.setMagic("","w","","")
    assert n.magicMarkup == TokenizingMarkup.ELEMENT_ONLY
    def collected = n.collectText()
    def wMagic = collected.split(/\s/)
    assert wMagic.size() == 2
    assert wMagic[1] == "goddess"

  }
}
