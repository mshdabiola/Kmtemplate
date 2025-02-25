### Design System Module Graph

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
    :modules:designsystem["designsystem"]
    :modules:ui["ui"]
    :modules:analytics["analytics"]
    :modules:model["model"]
    :modules:testing["testing"]
  end
  :features:main --> :modules:designsystem
  :app --> :modules:designsystem
  :modules:ui --> :modules:designsystem
  :modules:analytics --> :modules:designsystem
  :features:detail --> :modules:designsystem
  :features:setting --> :modules:designsystem
  :modules:designsystem --> :modules:model
  :modules:designsystem --> :modules:testing
  :modules:designsystem --> :modules:ui
  :modules:testing --> :modules:designsystem

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :modules:designsystem focus
```