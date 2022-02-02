<h1> Mentoria Java - Programa 02 </h1>

 Projeto proposto por [Thiago Pereira de Oliveira](https://github.com/thiagop-o) 
 
 Projeto feito por [Ricardo S. Murça](https://github.com/richardsm1) 


<h2> Api - Login </h2>

<hr/>

<p>Neste segundo desafio, iniciaremos a criação de um serviço, onde contará com vários microsserviços, o primeiro deles será o de LOGIN. 

Nossa API se comunicará com um banco de Dados MySQL e nele teremos 4 colunas com a seguinte estrutura:</p>

id        | usuario | senha  | funcao |
--------- | ------- | ------ | ------ | 
Long      | String  | String | String |

<p>As funções serão somente funcionário e admin.

Nesse nosso sistema, teremos uma controller que irá receber as requisições HTTP onde serão:</p>

- Get (somente admin conseguirá ver usuário cadastrados)
- Post (de cadastro de usuário)
- Post (de login de usuário)
- Delete (Somente admin poderá deletar um usuário)
- Put (Somente admin poderá atualizar um usuário cadastrado)

> <p>Lembrando: Validar os casos de uso quando um funcionário tentar deletar, atualizar ou ler os registros, devera retornar a mensagem: "Usuário não possui esse nível de acesso"<p>

<p>DOD (definition of done) </p>

- Cobertura de testes >= 85%
- Registros no banco de dados
- Validação dos casos
- Senha salva com criptografia 

<p>Qualquer dúvida estarei no Discord para Auxilia-los</p>

![discord logo](https://img.icons8.com/office/16/000000/discord-logo.png)  [thiagocrow#9854](https://discordapp.com/users/412300823234609153)
