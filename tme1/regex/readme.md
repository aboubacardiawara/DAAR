Ce projet est construit sous maven

## Installation

```bash
mvn clean install
```

## Execution des tests automatiques

Vous pouvez executer les tests automatiques avec la commande suivante:

```bash
mvn test
```

## Execution de l'application

Vous pouvez executer l'application avec la commande suivante:

```bash
mvn exec:java
```

Vous verrez la description de l'application et les commandes disponibles.

## Execution de l'application avec des paramètres

```bash
mvn exec:java -Dexec.args="<nom_du_fichier> <regex>"
```
