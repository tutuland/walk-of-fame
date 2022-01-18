import SwiftUI
import core

class ViewModel : ObservableObject{
    @Published var content: String = "loading"

    init() {
        load()
    }


    func load() -> Void {
        NativeSearchApi().searchFor(person: "Wes Anderson") { [weak self] result in
            if (result == "") {
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