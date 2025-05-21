// swift-tools-version:5.8
import PackageDescription

let packageName = "shared"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: packageName,
            targets: ["KmpSharedWrapper"]
        ),
    ],
    dependencies: [
        .package(url: "https://github.com/rickclephas/KMP-ObservableViewModel.git", exact: "1.0.0-BETA-8"),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            path: "./shared/build/XCFrameworks/debug/\(packageName).xcframework"
        ),
        .target(
          name: "KmpSharedWrapper",
          dependencies: [
              .target(name: packageName),
              .product(name: "KMPObservableViewModelCore", package: "KMP-ObservableViewModel"),
              .product(name: "KMPObservableViewModelSwiftUI", package: "KMP-ObservableViewModel"),
          ],
          path: "./shared/build/XCFrameworks/debug/Sources/KmpSharedWrapper"
                )
    ]
) 