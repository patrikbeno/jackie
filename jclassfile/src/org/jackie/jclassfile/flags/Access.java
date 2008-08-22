package org.jackie.jclassfile.flags;

/**
 * @author Patrik Beno
 */
public enum Access {

   PUBLIC(0x0001),
   PRIVATE(0x0002),
   PROTECTED(0x0004),
   STATIC(0x0008),
   FINAL(0x0010),
   SUPER(0x0020), SYNCHRONIZED(0x0020),
	VOLATILE(0x0040),
	TRANSIENT(0x0080),
	NATIVE(0x0100),
	INTERFACE(0x0200),
   ABSTRACT(0x0400),
   STRICT(0x0800),

	;

   private int value;

	private Access(int value) {
      this.value = value;
	}

   public int value() {
      return value;
   }
}
