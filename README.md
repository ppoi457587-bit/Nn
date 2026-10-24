# LISO AI — Otimização segura para Android

Projeto Android nativo em Java, interface preto/verde e workflow do GitHub Actions para compilar um APK de debug.

## Recursos reais
- Diagnóstico do fabricante, modelo, versão do Android, RAM total e RAM disponível reportada pelo sistema.
- Leitura da taxa de atualização da tela em Hz (não é FPS real do jogo).
- Limpeza do cache temporário pertencente ao próprio LISO AI.
- Atalhos para configurações de tela, bateria e opções de desenvolvedor.
- Guia de preparação para jogos.

## Limites do Android
Um aplicativo comum não pode limpar o cache de outros apps, encerrar à força jogos, fazer overclock, nem medir o FPS interno de qualquer jogo sem suporte/permissões especiais. O botão de otimização não promete mudanças impossíveis: limpa somente o cache próprio e mostra dados disponíveis.

## Gerar APK
Envie o conteúdo desta pasta para a raiz de um repositório GitHub. Em Actions, execute `Build LISO AI APK`. O arquivo será disponibilizado em Artifacts como `LISO-AI-APK`.

O projeto não foi testado em um aparelho físico; confirme instalação e funcionamento no dispositivo-alvo.
