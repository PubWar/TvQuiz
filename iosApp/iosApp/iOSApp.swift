import SwiftUI
import FirebaseMessaging
import FirebaseCore
import FirebaseAuth
import UserNotifications

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
//    init(){
//        FirebaseApp.configure()
//        if let firebaseApp = FirebaseApp.app() {
//            print("Firebase app initialized: \(firebaseApp.name)")
//        } else {
//            print("Firebase app failed to initialize.")
//        }
//    }
    
    var body: some Scene {
        
        WindowGroup {
           
            ContentView()
                .onOpenURL { url in
                         print("Received URL: \(url)")
                         Auth.auth().canHandle(url) // <- just for information purposes
                       }
        }
    }
}


class AppDelegate: UIResponder, UIApplicationDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
         print(">> your code here !!")
        
        
        if(FirebaseApp.app() == nil)
        {
            FirebaseApp.configure()
        }
        
        Messaging.messaging().delegate = self
        UNUserNotificationCenter.current().delegate = self
        registerForPushNotifications(application: application)
         return true
        
     }
    
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        if Auth.auth().canHandle(url) {
              return true
          }
          // Handle other URLs
          return false
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
        Auth.auth().setAPNSToken(deviceToken, type: .sandbox)
      }
}




extension AppDelegate: UNUserNotificationCenterDelegate {
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print(error)
    }
    
    
    private func registerForPushNotifications(application: UIApplication) {
        UNUserNotificationCenter.current().delegate = self
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]

        UNUserNotificationCenter.current().requestAuthorization(options: authOptions) {
            (granted, error) in
            guard granted else { return }
            DispatchQueue.main.async {
                application.registerForRemoteNotifications()
            }
        }
    }
    
//    func requestNotificationAuthorization(_ application: UIApplication) {
//        print("requestNotificationAuthorization")
//        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
//            if granted {
//                print("Notification permission is granted.")
//                DispatchQueue.main.async {
//                    application.registerForRemoteNotifications()
//                    print("Notification is registered")
//                }
//            } else {
//                print("Notification permission not granted.")
//            }
//        }
//    }
    
    
}





extension AppDelegate: MessagingDelegate {
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("Firebase registration token: \(String(describing: fcmToken))")
        
    }
}
