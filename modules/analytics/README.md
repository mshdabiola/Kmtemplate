### Analytics Module Graph

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
    :modules:analytics["analytics"]
    :modules:data["data"]
    :modules:ui["ui"]
    :modules:testing["testing"]
    :modules:designsystem["designsystem"]
    :modules:model["model"]
  end
  :features:main --> :modules:analytics
  :app --> :modules:analytics
  :modules:data --> :modules:analytics
  :modules:ui --> :modules:analytics
  :modules:analytics --> :modules:testing
  :modules:analytics --> :modules:designsystem
  :modules:analytics --> :modules:ui
  :modules:analytics --> :modules:model
  :features:detail --> :modules:analytics
  :features:setting --> :modules:analytics
  :modules:testing --> :modules:analytics

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :modules:analytics focus
```