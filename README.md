# Rodus

Rodus is a professional-grade JavaFX application for file encryption and decryption. It provides a user-friendly interface to encrypt and decrypt files using the AES encryption algorithm.

## Features

- Securely encrypt files with a password and PIN
- Decrypt encrypted files using the same password and PIN
- Utilizes the robust AES encryption algorithm
- Intuitive and visually appealing JavaFX interface

## Getting Started

### Prerequisites

- Java Runtime Environment (JRE) 11 or higher

### Installation

You can download the latest version of Rodus from the [Releases](https://github.com/ammardevz/Rodus/releases) page. Choose the appropriate installation file for your operating system:

- For Windows: Download the `.exe` or `.msi` installer file.
- For other platforms: Download the `.jar` file.

### Usage

1. Double-click the downloaded installation file to launch the installer.

   - For Windows: Follow the on-screen instructions to complete the installation.
   - For other platforms: The `.jar` file can be directly executed with the following command:

     ```
     java -jar rodus.jar
     ```
     Alternatively, you may be able to run the installation file normally without needing to execute any additional commands, as it contains the necessary dependencies           (such as DLLs or shared libraries) to run smoothly on your platform.

     Please note that the above instructions assume you have Java Runtime Environment (JRE) installed on your system. If you don't have it already, you will need to install      it before proceeding with the installation of rodus.jar.
2. Launch the Rodus application.

3. Select the files you want to encrypt or decrypt.

4. Enter a password and a PIN for encryption/decryption.

5. Click the "Encrypt" or "Decrypt" button to perform the operation.

6. The encrypted or decrypted files will be saved to the same location as the original files with the ".encrypted" or ".decrypted" extension, respectively.

## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

This project incorporates the following open-source software:

- [JavaFX](https://openjfx.io/) - JavaFX framework for building desktop applications
- [Apache Maven](https://maven.apache.org/) - Dependency management and build tool
- [Lombok](https://projectlombok.org/) - Simplify Java code with annotations

The project is licensed under the MIT License, which allows for the use, modification, and distribution of the software while providing the necessary disclaimers and limitations.
