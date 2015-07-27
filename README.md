# Xz encoder plugin for Embulk

Xz encoder plugin for Embulk. Compress data using XZ (also known as LZMA).

## Overview

* **Plugin type**: encoder

## Configuration

## Example

```yaml
out:
  type: any output input plugin type
  encoders:
    - {type: xz}
```


## Build

```
$ ./gradlew gem
```
