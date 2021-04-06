steps:

cd angular - getting into the angular project workspace
npm install - to download dependancys (like maven) 
ng serve    - to start development server.

Make sure your gitignore has this: 

target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
# https://github.com/takari/maven-wrapper#usage-without-binary-jar
.mvn/wrapper/maven-wrapper.jar

.vscode/*
!.vscode/settings.json
!.vscode/tasks.json
!.vscode/launch.json
!.vscode/extensions.json
*.code-workspace

# Local History for Visual Studio Code
.history/
.DS_Store



# all the angular stuff

angular/dist
angular/tmp
angular/out-tsc
# Only exists if Bazel was run
angular/bazel-out

# dependencies
angular/node_modules

# profiling files
angular/chrome-profiler-events*.json
angular/speed-measure-plugin*.json

# IDEs and editors
angular/.idea
angular/.project
angular/.classpath
angular/.c9/
angular/*.launch
angular/.settings/
angular/.sublime-workspace

# IDE - VSCode
angular/.vscode/*
angular/!.vscode/settings.json
angular/!.vscode/tasks.json
angular/!.vscode/launch.json
angular/!.vscode/extensions.json
angular/.history/*

# misc
angular/.sass-cache
angular/connect.lock
angular/coverage
angular/libpeerconnection.log
angular/npm-debug.log
angular/yarn-error.log
angular/testem.log
angular/typings

# System Files
angular/.DS_Store
angular/Thumbs.db

