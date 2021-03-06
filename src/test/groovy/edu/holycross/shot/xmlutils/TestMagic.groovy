package edu.holycross.shot.xmlutils

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestMagic {

  @Test
  void testMagicNodeName() {
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


  @Test
  void testMagicAttrName() {
    String xmlSrc = """
    <l>Sing, <w ana="token">god<unclear>dess</unclear>
    </w></l>
    """
    XmlNode n  = new XmlNode(xmlSrc)
    def noMagic = n.collectText().split(/\s/)
    assert noMagic.size() == 3
    assert noMagic[1] == "god"

    //define a magic node tokenizing on "w" element:
    n.setMagic("","","ana","")
    assert n.magicMarkup == TokenizingMarkup.ATTRIBUTE_ONLY
    def collected = n.collectText()
    def wMagic = collected.split(/\s/)
    assert wMagic.size() == 2
    assert wMagic[1] == "goddess"
  }


  @Test
  void testMagicAttrValue() {
    String xmlSrc = """
    <l>Sing, <w ana="token">god<unclear>dess</unclear>
    </w></l>
    """
    XmlNode n  = new XmlNode(xmlSrc)
    def noMagic = n.collectText().split(/\s/)
    assert noMagic.size() == 3
    assert noMagic[1] == "god"

    //define a magic node tokenizing on "w" element:
    n.setMagic("","","ana","token")
    assert n.magicMarkup == TokenizingMarkup.ATTRIBUTE_VALUE_ONLY
    def collected = n.collectText()
    def wMagic = collected.split(/\s/)
    assert wMagic.size() == 2
    assert wMagic[1] == "goddess"
  }





    @Test
    void testMagicAttrOnElem() {
      String xmlSrc = """
      <l>Sing, <w ana="token">god<unclear>dess</unclear>
      </w></l>
      """
      XmlNode n  = new XmlNode(xmlSrc)
      def noMagic = n.collectText().split(/\s/)
      assert noMagic.size() == 3
      assert noMagic[1] == "god"

      //define a magic node tokenizing on "w" element:
      n.setMagic("","w","ana","")
      assert n.magicMarkup == TokenizingMarkup.ATTRIBUTE_ON_ELEMENT
      def collected = n.collectText()
      def wMagic = collected.split(/\s/)
      assert wMagic.size() == 2
      assert wMagic[1] == "goddess"
    }




    @Test
    void testMagicAttrValOnElem() {
      String xmlSrc = """
      <l>Sing, <w ana="token">god<unclear>dess</unclear>
      </w></l>
      """
      XmlNode n  = new XmlNode(xmlSrc)
      def noMagic = n.collectText().split(/\s/)
      assert noMagic.size() == 3
      assert noMagic[1] == "god"

      //define a magic node tokenizing on "w" element:
      n.setMagic("","w","ana","token")
      assert n.magicMarkup == TokenizingMarkup.ATTRIBUTE_VALUE_ON_ELEMENT
      def collected = n.collectText()
      def wMagic = collected.split(/\s/)
      assert wMagic.size() == 2
      assert wMagic[1] == "goddess"


      String distinctAttVals = """<l><w ana="multitoken">Sing<punct>,</punct></w> <w ana="token">god<unclear>dess</unclear></w></l>"""

      XmlNode distinctAttNode = new XmlNode(distinctAttVals)
      distinctAttNode.setMagic("","","ana","token")
      assert distinctAttNode.magicMarkup == TokenizingMarkup.ATTRIBUTE_VALUE_ONLY

      def distinctCollected = distinctAttNode.collectText()
      def byAttVal = distinctCollected.split(/\s/)
      assert byAttVal.size() == 3
      assert byAttVal[1] == ","
    }
}
