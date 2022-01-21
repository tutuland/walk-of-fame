import SwiftUI
import core

class ViewModel : ObservableObject{
    @Published var content: String = "loading"
    let searchApi = ServiceLocator.shared.searchApi
    let detailsApi = ServiceLocator.shared.detailsApi
    
    init() {
        load()
    }
    
    
    func load() -> Void {
        searchApi.searchFor(person: "Wes Anderson") { [weak self] data, error in
            if (error != nil) {
                self?.content = "Failure!"
            } else {
                let id = data?.people?[0].id ?? 0
                self?.detailsApi.getDetailsFor(id: "\(id)") { [weak self] data, error in
                    if (error != nil) {
                        self?.content = "Failure!"
                    } else {
                        self?.content = "Success!"
                    }
                }
            }
        }
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
