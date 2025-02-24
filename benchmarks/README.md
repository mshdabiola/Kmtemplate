### Benchmarks Module Graph

```mermaid
%%{
  init: {
    'theme': 'base',
    'themeVariables': {"primaryTextColor":"#fff","primaryColor":"#5a4f7c","primaryBorderColor":"#5a4f7c","lineColor":"#f5a623","tertiaryColor":"#40375c","fontSize":"12px"}
  }
}%%

graph LR
  :benchmarks --> :app
  :app --> :benchmarks

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :benchmarks focus
```