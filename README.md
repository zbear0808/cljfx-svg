# cljfx-svg

SVG icon component for cljfx applications.

A simple, declarative SVG component for cljfx, powered by [fxsvgimage](https://fxsvgimage.sourceforge.io/).

## Features

- **Function component**: Use `{:fx/type svg/icon}` - no registration required
- **Full SVG support**: Paths, shapes, gradients, filters, clip paths, and more
- **Color control**: Override fill colors for monochrome icons
- **Automatic scaling**: Scale icons to any size while preserving aspect ratio
- **Standard JavaFX node**: Returns a Group with all standard node properties

## Installation

> **Note**: This library depends on [fxsvgimage](https://github.com/hervegirod/fxsvgimage), which is only available via JitPack. You must add JitPack to your repository configuration.

### deps.edn

```clojure
{:mvn/repos {"jitpack" {:url "https://jitpack.io"}}
 
 :deps {cljfx/cljfx {:mvn/version "1.10.6"}
        org.clojars.zubad/cljfx-svg {:mvn/version "0.1.0"}}}
```

### Leiningen

```clojure
:repositories [["jitpack" "https://jitpack.io"]]

:dependencies [[cljfx "1.10.6"]
               [org.clojars.zubad/cljfx-svg "0.1.0"]]
```

## Quick Start

```clojure
(ns my-app.core
  (:require [cljfx.api :as fx]
            [cljfx-svg.core :as svg]))

;; Use svg/icon as a function component (no registration needed)
{:fx/type :button
 :graphic {:fx/type svg/icon
           :src "icons/gear.svg"
           :size 24}}

;; With color override
{:fx/type :label
 :graphic {:fx/type svg/icon
           :src "icons/heart.svg"
           :size 16
           :color :red}}

;; Load from classpath resources or file paths
{:fx/type svg/icon
 :src "icons/star.svg"        ; classpath resource
 :size 20}

{:fx/type svg/icon
 :src "C:/path/to/icon.svg"   ; absolute file path
 :size 24}
```

## Props Reference

### SVG-Specific Props

| Prop | Type | Required | Default | Description |
|------|------|----------|---------|-------------|
| `:src` | string | **Yes** | - | Path to SVG file (classpath resource or file path) |
| `:size` | number | No | - | Target width in pixels (aspect ratio preserved) |
| `:color` | any | No | nil | Fill color override (keyword, string, or Paint) |

### Color Values

The `:color` prop accepts:
- Keywords: `:red`, `:blue`, `:transparent`
- CSS strings: `"#ff0000"`, `"rgb(255,0,0)"`, `"red"`
- JavaFX Paint instances: `Color/RED`, `(Color/rgb 255 0 0)`

## Full Example

See [`examples/toolbar_demo.clj`](examples/toolbar_demo.clj) for a complete working example.

## SVG Support

Powered by [fxsvgimage](https://fxsvgimage.sourceforge.io/), this library supports:

- **Shapes**: rect, circle, ellipse, path, polygon, polyline, line, image, text, tspan
- **Structure**: use, symbol, clip paths
- **Styling**: fill, stroke, style, class, transform attributes
- **Effects**: linear gradients, radial gradients, filters

For complete documentation, see the [fxsvgimage documentation](https://fxsvgimage.sourceforge.io/).

## Development

### Building & Deployment

```bash
clj -T:build jar
clj -T:build deploy
```

## License

MIT License

Copyright (c) 2024
