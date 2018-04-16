# Subset-C-Compiler

This is a compiler for a learning purpose.

It can compile subset of c language code to Java bytecode. There is BNF of the language in this repository (see SubsetC.g4 file).

This language has only the following features.
* declare global/local variables
* use int type only
* do four arithmetic operations 
* declare functions
* call function
* use if or while statement and comparison operators

## Sample
Here is sample code. Save this in Test.jyu file.

```
int i;
void main() {
  i = 2;
  int x;
  x = 3;
  if (i < x) {
    mul(i, x);
  }
}
int mul(int a, int b) {
  return a * b;
}
```

This sample code can be compiled with this command.

```
com.sakatakoichi.subsetc.compiler.Compiler Test.jyu
```

The compiler generates `Test.class` file.

* global variable is translated into Java static variable.
* function is translated into Java static method.

You can see Java bytecode with `javap` command.

```
$ javap -v Test
Classfile /Test.class
  Last modified 2018/04/16; size 214 bytes
  MD5 checksum 6a83da14906e8a09f82fdba3c412777f
class Test
  minor version: 0
  major version: 52
  flags: (0x0020) ACC_SUPER
  this_class: #2                          // Test
  super_class: #4                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 2, attributes: 0
Constant pool:
   #1 = Utf8               Test
   #2 = Class              #1             // Test
   #3 = Utf8               java/lang/Object
   #4 = Class              #3             // java/lang/Object
   #5 = Utf8               i
   #6 = Utf8               I
   #7 = NameAndType        #5:#6          // i:I
   #8 = Fieldref           #2.#7          // Test.i:I
   #9 = Utf8               ()V
  #10 = Utf8               main
  #11 = NameAndType        #10:#9         // main:()V
  #12 = Methodref          #2.#11         // Test.main:()V
  #13 = Utf8               Code
  #14 = Utf8               (II)I
  #15 = Utf8               mul
  #16 = NameAndType        #15:#14        // mul:(II)I
  #17 = Methodref          #2.#16         // Test.mul:(II)I
{
  public static void main();
    descriptor: ()V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: getstatic     #8                  // Field i:I
         3: if_icmpge     20
         6: nop
         7: iload_0
         8: getstatic     #8                  // Field i:I
        11: invokestatic  #17                 // Method mul:(II)I
        14: pop
        15: iconst_3
        16: istore_0
        17: iconst_2
        18: putstatic     #8                  // Field i:I
        21: return

  public static int mul(int, int);
    descriptor: (II)I
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=2
         0: iload_0
         1: iload_1
         2: imul
         3: ireturn
}
``` 

Here is disassemble Java code from the class file.

```java
class Test
{
  private static int i;
  
  public static void main()
  {
    int j;
    if (i < j)
    {
      mul(j, i);
      j = 3;
      i = 2;
    }
  }
  
  public static int mul(int paramInt1, int paramInt2)
  {
    return paramInt1 * paramInt2;
  }
}
```

## Resource

I referred to sample code in the book "Compiler and Virtual Machine" (not copied code).

[IT Text コンパイラとバーチャルマシン \| Ohmsha](https://www.ohmsha.co.jp/book/9784274133084/)