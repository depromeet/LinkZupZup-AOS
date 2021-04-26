package com.depromeet.linkzupzup.dataSources.repositories

class TagRepositoryImpl: TagRepository {

    override fun getTagList(): ArrayList<String> {
        // dataSource (API or Database) 를 참조하여 데이터를 반환합니다.
        return arrayListOf<String>().apply {
            add("#디자인")
            add("#포트폴리오")
            add("#UX")
            add("#UI")
            add("#마케팅")
            add("#인공지능")
            add("#프론트 개발")
            add("#그로스 해킹")
            add("#Android")
            add("#스타트업")
            add("#IOS")
        }
    }

}