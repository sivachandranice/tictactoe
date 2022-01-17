#!groovy
def appData = [
    appName: "tictactoe",
    portfolio: "test",
    triggerCodeBuild: true,
    triggerCodeBuildPostPromotion: false,
    s3upload: [
        [
            file: "target/tictactoedemo-0.0.1-SNAPSHOT.jar",
            folder: "files/build/",
            bucketName: "tictactoe",
            dependenciesConfig: "https://test.sq.com.sg/scm/tictac/tictactoe-deployment-config.git",
            componentName: "component1"
        ]
    ]
]
if (env.BRANCH_NAME == "master") {
    echo "Production realses should go through release pipeline"
} else if (env.BRANCH_NAME == "integration") {
    promote(appData)
} else {
    dev_maven(appData)
}