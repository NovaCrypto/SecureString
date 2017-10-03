[![Download](https://api.bintray.com/packages/novacrypto/SecureString/SecureString/images/download.svg) ](https://bintray.com/novacrypto/SecureString/SecureString/_latestVersion)

Read the motivation here: [Protecting Strings in JVM memory](https://medium.com/@_west_on/protecting-strings-in-jvm-memory-84c365f8f01c)

# Install

Use either of these repositories:

```
repositories {
    jcenter()
}
```

Or:

```
repositories {
    maven {
        url 'https://dl.bintray.com/novacrypto/SecureString/'
    }
}
```

Add dependency:

```
dependencies {
    compile 'io.github.novacrypto:SecureString:0.1.5@jar'
}

```

# Usage
## Create

```
new SecureCharBuffer()
```

For default capacity or 512 chars, or specify capacity with:

```
SecureCharBuffer.withCapacity(30)
```

## Append

```
buffer.append('a')
```

## Read

```
final int length = buffer.length();
for (int i = 0; i < length; i++) {
    buffer.get(i)
}
```

## Clear

```
buffer.close()
```