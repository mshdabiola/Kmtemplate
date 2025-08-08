### Main Module Graph

```mermaid
%%{
  init: {
    'theme': 'base',
    'themeVariables': {"primaryTextColor":"#fff","primaryColor":"#5a4f7c","primaryBorderColor":"#5a4f7c","lineColor":"#f5a623","tertiaryColor":"#40375c","fontSize":"12px"}
  }
}%%

graph LR
  subgraph :features
    :features:main["main"]
  end
  subgraph :modules
    :modules:data["data"]
    :modules:model["model"]
    :modules:ui["ui"]
    :modules:designsystem["designsystem"]
    :modules:analytics["analytics"]
    :modules:testing["testing"]
  end
  :features:main --> :modules:data
  :features:main --> :modules:model
  :features:main --> :modules:ui
  :features:main --> :modules:designsystem
  :features:main --> :modules:analytics
  :features:main --> :modules:testing
  :app --> :features:main

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :features:main focus
```