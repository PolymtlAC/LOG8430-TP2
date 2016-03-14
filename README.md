# LOG8430-TP2 : Mise en Oeuvre d'une Architecture Logicielle et Chargement Dynamique
## Option 1 : Gestion de fichier
Alexandre Chenieux - Thomas Neyraut - Alexandre Pereira

Ce logiciel permet d'exécuter des commandes sur des dossiers et des fichiers. Une arborescence permet à l'utilisateur de naviguer à travers les dossiers et les fichiers. Pour pouvoir exécuter une commande l'utilisateur doit préalablement sélectionner un dossier ou un fichier. Initialement, la racine sélectionnée par le logiciel et le répertoire "Racine" de l'utilisateur. Enfin via l'interface graphique, l'utilisateur peut choisir une autre racine pour l'arborescence.

Certaines commandes ne peuvent être exécuter que pour des fichiers ou des dossiers. Dans ce cas, l'interface graphique s'adapte et certaines commandes sont "grisées" et désactivées. Si l'option "AutoRun" est activé, l'exécution des commandes accessibles est automatique dès la sélection d'un dossier ou d'un fichier. Enfin, un bouton clear permet d'effacer les différents résultats des commandes.

Les commandes sont chargés automatiquement au lancement du logiciel. L'utilisateur peut ajouter ses propres commandes. Il doit les implémenter sous la forme d'un plugin (un plugin par commande) tout en respectant l'interface Command. Ensuite, l'utilisateur doit modifier le fichier de configuration du programme pour ajouter ses plugins de commande. 

Pour exécuter le logiciel avec Eclipse, il faut : 
- Ouvrir le projet avec Eclipse,
- Ouvrir le fichier tp2.product,
- Aller dans l'onglet Overview,
- Aller dans le sous-menu Testing,
- Cliquer sur Launch an Eclipse application.

Exemple d'une commande retournant le nom d'un fichier : 

    import java.io.File;
    public class FileNameCommand implements Command {

        // méthode permettant de définir la commande (résultat de la commande)
        @Override
        public String execute(File file) {
            return file.getName();
        }
        
        // méthode définissant si la commande est exécutable pour un fichier
        @Override
        public boolean fileCompatible() {
            return true;
        }
            
        // méthode définissant si la commande est exécutable pour un dossier
        @Override
        public boolean folderCompatible() {
            return false;
        }
            
        // méthode définissant le nom de la commande apparaissant sur l'inteface graphique
        @Override
        public String getName() {
            return "File name";
        }
        
    }


### Have Fun !
