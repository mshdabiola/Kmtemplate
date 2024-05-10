package com.mshdabiola.network.request

import io.ktor.resources.Resource

@Resource("/article")
class Articles() {
    @Resource("{id}")
    class Id(val parent: Articles = Articles(), val id: Long)
}

@Resource("/article")
class Articles2(val sort: String? = "news")

@Resource("/article")
class Articles3() {
    @Resource("new")
    class New(val parent: Articles = Articles())

    @Resource("{id}")
    class Id(val parent: Articles = Articles(), val id: Long) {
        @Resource("edit")
        class Edit(val parent: Id)
    }
}
