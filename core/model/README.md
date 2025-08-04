### Model Module Graph

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
    :modules:model["model"]
    :modules:testing["testing"]
    :modules:database["database"]
    :modules:data["data"]
    :modules:ui["ui"]
    :modules:datastore["datastore"]
    :modules:analytics["analytics"]
    :modules:designsystem["designsystem"]
  end
  :modules:model --> :modules:testing
  :modules:database --> :modules:model
  :features:main --> :modules:model
  :app --> :modules:model
  :modules:data --> :modules:model
  :modules:ui --> :modules:model
  :modules:datastore --> :modules:model
  :modules:analytics --> :modules:model
  :features:detail --> :modules:model
  :features:setting --> :modules:model
  :modules:designsystem --> :modules:model
  :modules:testing --> :modules:model

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :modules:model focus
```