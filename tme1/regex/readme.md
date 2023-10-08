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

Ouvrir le fichier pom.xml et modifier la valeur de la propriété "exec.args" dans la balise "configuration" de la balise "plugin" "exec-maven-plugin" comme suit:

```xml
<configuration>
    <mainClass>com.daar.App</mainClass>
    <arguments>
        <argument>note.txt</argument>
        <argument>S(a|r|g)+on</argument>
    </arguments>
</configuration>
```
