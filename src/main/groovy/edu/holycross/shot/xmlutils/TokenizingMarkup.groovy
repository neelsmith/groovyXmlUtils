package edu.holycross.shot.xmlutils

/** A class enumerating the degree quality of adjectives
* and adverbs.  Enumerated values may be expressed with human-readable
* labels, or in the multicharacter symbols used in the
* morphological parser's FST engine.
*/
public enum TokenizingMarkup {

  /** Specific element tokenizes. */
  ELEMENT_ONLY ("Any occurrence of specified element tokenizes"),
  /** Specific attribute tokenizes, regardless of element it's on. */
  ATTRIBUTE_ONLY("Any occurrence of specified attribute tokenizes"),
  /** Specific value on specific attribute tokenizes, regardless of element it's on. */
  ATTRIBUTE_VALUE_ONLY("Any occurrence of specified value on specified attribute tokenizes"),
  /** Specific attribute on specific tokenizes, regardless of element it's on. */
  ATTRIBUTE_ON_ELEMENT("Any occurrence of specified attribute on specified element tokenizes"),
  /** Specific attribute on specific attribute on specific element tokenizes*/
  ATTRIBUTE_VALUE_ON_ELEMENT("Any occurrence of specified value of specified attribute on specified element tokenizes")

  private String label

  private TokenizingMarkup(String label) {
    this.label = label
  }

  /** Gets a human-readable label for this value. */
  public String getLabel() {
    return label
  }
}
