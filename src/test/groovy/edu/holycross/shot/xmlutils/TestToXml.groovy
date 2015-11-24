package edu.holycross.shot.xmlutils

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestToXml {

  @Test
  void testSerialize(){
    String asciiSource = """
    <p n="1">ABG</p>
    """
    // Go around the horn:
    XmlNode n = new XmlNode(asciiSource)
    XmlNode deriv = new XmlNode(n.toXml())
    assert n.collectText() == deriv.collectText()
  }

  @Test
  void testMagic() {
    String xmlSrc = """
    <l>Sing, <w>god<unclear>dess</unclear>
    </w></l>
    """

    XmlNode n = new XmlNode(xmlSrc)
    String serialized = n.toXml()
    XmlNode deriv = new XmlNode(serialized)
    System.err.println "DERIV XML " + deriv.toXml()
    deriv.setMagic("","w","","")
    assert deriv.collectText() == "Sing, goddess"
  }

}
