import SwiftUI
import core

let injector = KoinIOSKt.injectOnIOS().koin

class ViewModel : ObservableObject{
    @Published var content: String = "searching for Chadwick"

    init() {
        load()
    }

    func load() -> Void {
        let log = injector.getLoggerWith(tag: "Driver iOS")
        let searchViewModel = injector.getSearchViewModel()
        let detailsViewModel = injector.getDetailsViewModel()
        searchViewModel.onViewState { [weak self] searchState in
            if (searchState.searchResults.count > 0) {
                self?.content += "\nsearch complete"
                log.d { "Received Search: \(searchState)" }
                detailsViewModel.requestDetailsWith(id: "172069")
            }
        }
        detailsViewModel.onViewState { [weak self] detailsState in
            if (detailsState.details != nil) {
                self?.content += "\ndetails received"
                log.d { "Received Details: \(detailsState)" }
            }
        }
        searchViewModel.searchFor(personName: "Chadwick")
    }
}

struct ContentView: View {
    @ObservedObject
    var viewModel = ViewModel()
    
    var body: some View {
        Text(viewModel.content)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
