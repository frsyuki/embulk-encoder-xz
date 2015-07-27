# Xz encoder plugin for Embulk

TODO: Write short description here and build.gradle file.

## Overview

* **Plugin type**: encoder

## Configuration

- **option1**: description (integer, required)
- **option2**: description (string, default: `"myvalue"`)
- **option3**: description (string, default: `null`)

## Example

```yaml
out:
  type: any output input plugin type
  encoders:
    - type: xz
      option1: example1
      option2: example2
```


## Build

```
$ ./gradlew gem
```
