### DataStore Module Graph

```mermaid
%%{
  init: {
    'theme': 'base',
    'themeVariables': {"primaryTextColor":"#fff","primaryColor":"#5a4f7c","primaryBorderColor":"#5a4f7c","lineColor":"#f5a623","tertiaryColor":"#40375c","fontSize":"12px"}
  }
}%%

graph LR
  subgraph :modules
    :modules:data["data"]
    :modules:datastore["datastore"]
    :modules:model["model"]
    :modules:testing["testing"]
  end
  :modules:data --> :modules:datastore
  :modules:datastore --> :modules:model
  :modules:datastore --> :modules:testing

classDef focus fill:#FA8140,stroke:#fff,stroke-width:2px,color:#fff;
class :modules:datastore focus
```