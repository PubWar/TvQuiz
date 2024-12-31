import UIKit
import SwiftUI
import composeApp

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
 
struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all).preferredColorScheme(.dark)// Compose has own keyboard handler
    }
}



