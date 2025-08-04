### Data Module Graph

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
    :modules:database["database"]
    :modules:model["model"]
    :modules:testing["testing"]
    :modules:data["data"]
    :modules:datastore["datastore"]
    :modules:network["network"]
    :modules:analytics["analytics"]
  end
  :modules:database --> :modules:model
  :modules:database --> :modules:testing
  :features:main --> :modules:data
  :app --> :modules:data
  :modules:data --> :modules:database
  :modules:data --> :modules:datastore
  :modules:data --> :modules:network
  :modules:data --> :modules:model
  :modules:data --> :modules:analytics
  :modules:data --> :modules:testing
  :modules:datastore --> :modules:model
  :modules:datastore --> :modules:testing
  :features:detail --> :modules:data
  :features:setting --> :modules:data
  :modules:testing --> :modules:data

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :modules:database focus
class :modules:data focus
class :modules:datastore focus
```