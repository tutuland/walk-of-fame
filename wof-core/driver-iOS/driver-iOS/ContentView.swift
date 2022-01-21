import SwiftUI
import core

class ViewModel : ObservableObject{
    @Published var content: String = "loading"
    let api = ServiceLocator.shared.searchApi

    init() {
        load()
    }


    func load() -> Void {
        api.searchFor(person: "Wes Anderson") { [weak self] data, error in
            if (error != nil) {
                self?.content = "Failure!"
            } else {
                self?.content = "Success!"
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
