[![Download](https://api.bintray.com/packages/novacrypto/SecureString/SecureString/images/download.svg)](https://bintray.com/novacrypto/SecureString/SecureString/_latestVersion) [![Travis](https://travis-ci.org/NovaCrypto/SecureString.svg?branch=master)](https://travis-ci.org/NovaCrypto/SecureString) [![codecov](https://codecov.io/gh/NovaCrypto/SecureString/branch/master/graph/badge.svg)](https://codecov.io/gh/NovaCrypto/SecureString)

Read the motivation here: [Protecting Strings in JVM memory](https://medium.com/@_west_on/protecting-strings-in-jvm-memory-84c365f8f01c)

# Install

Using:

```
repositories {
    mavenCentral()
}
```

Add dependency:

```
dependencies {
    implementation 'io.github.novacrypto:SecureString:2022.01.17@jar'
}
```

# Usage
## Create

```
new SecureCharBuffer()
```

For default capacity of 512 chars, or specify capacity with:

```
SecureCharBuffer.withCapacity(30)
```

## Append

```
buffer.append('a')
buffer.append("abc")
buffer.append(charSequence)
```

## Read

Can be used wherever a `CharSequence` can, so you can access the buffer one char at a time like so:

```
final int length = buffer.length();
for (int i = 0; i < length; i++) {
    buffer.get(i)
}
```

It supports `CharSequence#subSequence` by creating a proxy. This means that you do not need to worry about sub-sequences leaking string data. Sub-sequences of sub-sequences are also cleared.

```
buffer = new SecureBuffer();
buffer.append('a');
buffer.append('b');
buffer.append('c');
CharSequence subBuffer = buffer.subSequence(1, 2);
CharSequence subSubBuffer = subBuffer.subSequence(0, 1);
buffer.close(); // subBuffer and subSubBuffer are also cleared
```

It does however refuse to allow `toString` as that defeats the point, so you will get a `UnsupportedOperationException` if you try. If you have to for whatever reason, then you can get a proxy that allows it by:

```
   CharSequence iCanToString = secureBuffer.toStringAble();
   System.out.println(iCanToString.toString());
```

This `toString` generates on the demand, and does not cache the string value.

## Clear

```
buffer.close()
```

## Changelog

2019.01.27 contains a bug fix for characters outside standard ASCII range 0..127 which would not have been read correctly before.
2022.01.17 uses static `SecureRandom` on the advice of Spotbugs, and while it was a false positive intended for `Random` use warning, it's not a bad thing to do anyway.
