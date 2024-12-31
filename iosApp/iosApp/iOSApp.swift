import SwiftUI
import FirebaseMessaging
import FirebaseCore
import FirebaseAuth
import UserNotifications

@main
struct iOSApp: App {
//    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    init(){
        FirebaseApp.configure()
        if let firebaseApp = FirebaseApp.app() {
            print("Firebase app initialized: \(firebaseApp.name)")
        } else {
            print("Firebase app failed to initialize.")
        }
    }
    
    var body: some Scene {
        
        WindowGroup {
           
            ContentView()
        }
    }
}


class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
         print(">> your code here !!")
        
        
        if(FirebaseApp.app() == nil)
        {
            FirebaseApp.configure()
        }
        
        
        UNUserNotificationCenter.current().delegate = self
        
        requestNotificationAuthorization(application)
        
         return true
        
     }
    
    
    func application(_ application: UIApplication,
                     didReceiveRemoteNotification notification: [AnyHashable : Any],
                     fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        
        if Auth.auth().canHandleNotification(notification) {
               completionHandler(.noData)
               return
            
        }
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
          // Pass the device token to Firebase for registration
          Messaging.messaging().apnsToken = deviceToken
      }

      func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
          print("Failed to register for remote notifications: \(error.localizedDescription)")
      }
    
    
    func requestNotificationAuthorization(_ application: UIApplication) {
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
            if granted {
                DispatchQueue.main.async {
                    application.registerForRemoteNotifications()
                }
            } else {
                print("Notification permission not granted.")
            }
        }
    }
}
