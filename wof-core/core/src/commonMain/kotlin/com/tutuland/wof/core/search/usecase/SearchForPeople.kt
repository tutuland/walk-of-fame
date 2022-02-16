package com.tutuland.wof.core.search.usecase

import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.api.SearchApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class SearchForPeople(private val api: SearchApi) : Search.ForPeople {
    override fun withName(name: String): Flow<Search.Model> = flow {
        val result = getPeopleWith(name)
        mapPeopleFrom(result.people)
    }

    private suspend fun getPeopleWith(name: String): SearchApi.Result {
        val result = api.searchFor(name)
        if (result.isValid.not()) throw NoSearchResultsException(name)
        return result
    }

    private suspend fun FlowCollector<Search.Model>.mapPeopleFrom(people: List<SearchApi.Result.Person>?) {
        people.orEmpty()
            .filter { it.isValid }
            .forEach { person -> emit(person.mappedToDomain()) }
    }

    private fun SearchApi.Result.Person.mappedToDomain() = Search.Model(
        id = id.toString(),
        name = name.orEmpty(),
        department = mapDepartment(),
    )

    private fun SearchApi.Result.Person.mapDepartment(): String = department
        ?.let { "Known for $it" }
        .orEmpty()
}

class NoSearchResultsException(name: String) : Exception("Search for person failed for: $name")
