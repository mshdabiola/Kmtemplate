### UI Module Graph

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
    :features:detail["detail"]
    :features:setting["setting"]
  end
  subgraph :modules
    :modules:ui["ui"]
    :modules:analytics["analytics"]
    :modules:designsystem["designsystem"]
    :modules:model["model"]
    :modules:testing["testing"]
  end
  :features:main --> :modules:ui
  :app --> :modules:ui
  :modules:ui --> :modules:analytics
  :modules:ui --> :modules:designsystem
  :modules:ui --> :modules:model
  :modules:ui --> :modules:testing
  :modules:analytics --> :modules:ui
  :features:detail --> :modules:ui
  :features:setting --> :modules:ui
  :modules:designsystem --> :modules:ui
  :modules:testing --> :modules:ui

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :modules:ui focus
```