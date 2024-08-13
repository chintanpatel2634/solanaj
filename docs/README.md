# SolanaJ

Solana blockchain client, written in pure Java.
Solanaj is an API for integrating with Solana blockchain using the [Solana RPC API](https://docs.solana.com/apps/jsonrpc-api)

This fork includes functionality for multiple Solana programs, including the Serum DEX.

# SolanaJ-Programs
For SolanaJ implementations of popular Solana programs such as Serum, please visit: https://github.com/skynetcap/solanaj-programs

## Requirements
- Java 17+

## Dependencies
- bitcoinj
- OkHttp
- Moshi

## Installation
1. Add Maven dependency:

```xml
<dependency>
      <groupId>com.mmorrell</groupId>
      <artifactId>solanaj</artifactId>
      <version>1.17.6</version>
</dependency>
```

## Build
In pom.xml update the plugin maven-gpg-plugin configuration with your homedir and keyname.  
To see if you have a gpg key run `gpg --list-secret-keys`  
If nothing is returned create one with `gpg --full-generate-key`  
Then run `mvn install` and the build should complete successfully.  
```xml
<configuration>
    <homedir>/home/phil/.gnupg/</homedir>
    <keyname>AE2D00367F40E980F7C62FF792C4533F3EE03477</keyname>
</configuration>
```

## Example
##### Transfer lamports

```java
RpcClient client = new RpcClient(Cluster.TESTNET);

PublicKey fromPublicKey = new PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
PublicKey toPublickKey = new PublicKey("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
int lamports = 3000;

Account signer = new Account(secret_key);

Transaction legacyTransaction = new Transaction();
legacyTransaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, lamports));

String signature = client.getApi().sendTransaction(legacyTransaction, signer);
```

##### Get balance

```java
RpcClient client = new RpcClient(Cluster.TESTNET);

long balance = client.getApi().getBalance(new PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"));
```

##### Get Serum market + orderbooks
```java
final PublicKey solUsdcPublicKey = new PublicKey("7xMDbYTCqQEcK2aM9LbetGtNFJpzKdfXzLL5juaLh4GJ");
final Market solUsdcMarket = new MarketBuilder()
        .setClient(new RpcClient())
        .setPublicKey(solUsdcPublicKey)
        .setRetrieveOrderBooks(true)
        .build();

final OrderBook bids = solUsdcMarket.getBidOrderBook();
```

##### Send a legacyTransaction with call to the "Memo" program
```java
// Create account from private key
final Account feePayer = new Account(Base58.decode(new String(data)));
final Transaction legacyTransaction = new Transaction();

// Add instruction to write memo
legacyTransaction.addInstruction(
        MemoProgram.writeUtf8(feePayer.getPublicKey(),"Hello from SolanaJ :)")
);

String response = result = client.getApi().sendTransaction(legacyTransaction, feePayer);
```

## License

MIT License