# NABootAuth
Auth system for NA Boot Plan. \
NA Boot Plan is a full-stack web development framework that aims to provide swift construction for web applications. \
This project provides access control service for all systems in NA Boot Plan.

## Quick Start (Recommended)
[NA Boot Containerization](https://github.com/teimichael/NABootContainerization) provides a container-based deployment scaffold to facilitate development.

## Set Up
Maven Compile
- Dev
```
mvn clean package -D maven.test.skip=true -P dev
```
- Prod
```
mvn clean package -D maven.test.skip=true -P prod
```

## Related Projects
- [NA Boot Auth](https://github.com/teimichael/NABootAuth)
- [NA Boot Web](https://github.com/teimichael/NABootWeb)
- [NA Boot App](https://github.com/teimichael/NABootApp)
- [NA Boot Socket](https://github.com/teimichael/NABootSocket)