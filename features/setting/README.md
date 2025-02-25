### Setting Module Graph

```mermaid
%%{
  init: {
    'theme': 'base',
    'themeVariables': {"primaryTextColor":"#fff","primaryColor":"#5a4f7c","primaryBorderColor":"#5a4f7c","lineColor":"#f5a623","tertiaryColor":"#40375c","fontSize":"12px"}
  }
}%%

graph LR
  subgraph :features
    :features:setting["setting"]
  end
  subgraph :modules
    :modules:data["data"]
    :modules:model["model"]
    :modules:ui["ui"]
    :modules:designsystem["designsystem"]
    :modules:analytics["analytics"]
    :modules:testing["testing"]
  end
  :app --> :features:setting
  :features:setting --> :modules:data
  :features:setting --> :modules:model
  :features:setting --> :modules:ui
  :features:setting --> :modules:designsystem
  :features:setting --> :modules:analytics
  :features:setting --> :modules:testing

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :features:setting focus
```
# :feature:bookmarks module

![Dependency graph](../../docs/images/graphs/dep_graph_feature_bookmarks.png)