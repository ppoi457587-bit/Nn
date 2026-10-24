package com.lisoai.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(7, 12, 10);
    private static final int PANEL = Color.rgb(15, 24, 19);
    private static final int PANEL2 = Color.rgb(21, 33, 26);
    private static final int GREEN = Color.rgb(63, 232, 142);
    private static final int WHITE = Color.rgb(241, 248, 243);
    private static final int MUTED = Color.rgb(151, 171, 158);
    private LinearLayout root;
    private String activeProfile = "Equilibrado";
    private boolean gameMode = false;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        if (Build.VERSION.SDK_INT >= 23) getWindow().getDecorView().setSystemUiVisibility(0);
        showHome();
    }

    private void showHome() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        LinearLayout content = column();
        content.setPadding(dp(20), dp(18), dp(20), dp(22));
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout brandRow = row();
        LinearLayout logo = new LinearLayout(this); logo.setGravity(Gravity.CENTER); logo.setBackground(round(GREEN, 15));
        TextView logoText = text("L", 27, BG, true); logo.addView(logoText); brandRow.addView(logo, size(48,48));
        LinearLayout brandNames = column(); brandNames.setPadding(dp(12),0,0,0);
        brandNames.addView(text("LISO AI", 23, WHITE, true));
        TextView tag = text("GAMING CONTROL CENTER", 10, GREEN, true); tag.setLetterSpacing(.12f); brandNames.addView(tag);
        brandRow.addView(brandNames, new LinearLayout.LayoutParams(0,-2,1));
        TextView dot = text("● ONLINE", 10, GREEN, true); brandRow.addView(dot);
        content.addView(brandRow);

        LinearLayout hero = column(); hero.setPadding(dp(19),dp(20),dp(19),dp(19)); hero.setBackground(gradient(PANEL2, Color.rgb(21,48,32), 24));
        LinearLayout.LayoutParams hp = margin(-1,-2,0,22,0,0); content.addView(hero,hp);
        TextView eyebrow = text("SEU CENTRO DE COMANDO", 10, GREEN, true); eyebrow.setLetterSpacing(.14f); hero.addView(eyebrow);
        TextView headline = text("Entre no jogo.\nNo seu melhor.", 29, WHITE, true); LinearLayout.LayoutParams hlp=margin(-1,-2,0,10,0,0); hero.addView(headline,hlp);
        hero.addView(text("Prepare seu aparelho com ferramentas úteis e informações reais do sistema.", 13, MUTED, false));
        Button boost = primaryButton(gameMode ? "MODO JOGO ATIVADO" : "ATIVAR MODO JOGO");
        hero.addView(boost, margin(-1,dp(50),0,18,0,0));
        boost.setOnClickListener(v -> { gameMode = !gameMode; Toast.makeText(this, gameMode ? "Perfil de jogo ativado no LISO AI" : "Perfil de jogo desativado", Toast.LENGTH_SHORT).show(); showHome(); });
        hero.addView(text("O modo do LISO AI organiza suas ferramentas; ele não altera limites internos do Android.", 10, MUTED, false), margin(-1,-2,0,10,0,0));

        content.addView(sectionTitle("VISÃO GERAL", "STATUS DO APARELHO"), margin(-1,-2,0,23,0,10));
        LinearLayout stats = row();
        stats.addView(statCard("ANDROID", ""+Build.VERSION.RELEASE, "Versão do sistema"), new LinearLayout.LayoutParams(0,-2,1));
        View gap = new View(this); stats.addView(gap, size(10,1));
        stats.addView(statCard("FABRICANTE", Build.MANUFACTURER, Build.MODEL), new LinearLayout.LayoutParams(0,-2,1));
        content.addView(stats);
        content.addView(statCard("MEMÓRIA RAM", getRamSummary(), "Estimativa informada pelo Android"), margin(-1,-2,0,10,0,0));

        content.addView(sectionTitle("ACESSO RÁPIDO", "FERRAMENTAS"), margin(-1,-2,0,23,0,10));
        addFeature(content,"01","Otimização rápida","Limpa temporários do LISO e mostra estado da memória", "OTIMIZAR", this::runSafeOptimization);
        addFeature(content,"02","Tela e fluidez","Acesse taxa de atualização e opções de tela", "ABRIR", () -> openSettings(Settings.ACTION_DISPLAY_SETTINGS));
        addFeature(content,"03","Bateria e energia","Acesse economia e opções de energia", "ABRIR", () -> openSettings(Settings.ACTION_BATTERY_SAVER_SETTINGS));
        addFeature(content,"04","Painel FPS","Mostra Hz da tela e informações disponíveis", "ABRIR", this::showFpsPanel);
        addFeature(content,"05","Diagnóstico","Veja modelo, RAM disponível e Android", "VER DADOS", this::showDeviceInfo);
        addFeature(content,"06","Modo jogo","Preparação e recomendações antes de jogar", "PREPARAR", this::showGamePreparation);
        addFeature(content,"07","Desenvolvedor","Opções avançadas do Android", "ABRIR", () -> openSettings(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));

        LinearLayout tip = column(); tip.setPadding(dp(15),dp(14),dp(15),dp(14)); tip.setBackground(round(PANEL,16));
        content.addView(tip, margin(-1,-2,0,19,0,0));
        tip.addView(text("DICA LISO", 11, GREEN, true));
        tip.addView(text("Antes de jogar, feche downloads ativos, mantenha o aparelho ventilado e escolha configurações gráficas compatíveis com seu celular.", 12, MUTED, false), margin(-1,-2,0,6,0,0));

        LinearLayout nav = row(); nav.setGravity(Gravity.CENTER); nav.setPadding(dp(8),dp(10),dp(8),dp(10)); nav.setBackgroundColor(Color.rgb(11,17,13));
        root.addView(nav, size(-1,68));
        nav.addView(navItem("⌂", "Início", true), new LinearLayout.LayoutParams(0,-1,1));
        nav.addView(navItem("◈", "Perfil", false), new LinearLayout.LayoutParams(0,-1,1));
        nav.addView(navItem("⚙", "Ajustes", false), new LinearLayout.LayoutParams(0,-1,1));
        setContentView(root);
    }

    private View navItem(String symbol, String label, boolean selected) {
        LinearLayout item = column(); item.setGravity(Gravity.CENTER); item.setBackground(round(selected ? PANEL2 : BG, 12));
        item.addView(text(symbol, 20, selected ? GREEN : MUTED, true));
        item.addView(text(label, 10, selected ? WHITE : MUTED, true));
        item.setOnClickListener(v -> {
            if (label.equals("Perfil")) chooseProfile();
            else if (label.equals("Ajustes")) showSettingsInfo();
            else showHome();
        });
        return item;
    }

    private void chooseProfile() {
        String[] profiles = {"Economia", "Equilibrado", "Desempenho"};
        new AlertDialog.Builder(this).setTitle("Perfil de jogo")
            .setSingleChoiceItems(profiles, Math.max(0, java.util.Arrays.asList(profiles).indexOf(activeProfile)), (dialog, which) -> {
                activeProfile = profiles[which]; dialog.dismiss();
                Toast.makeText(this, "Perfil selecionado: " + activeProfile, Toast.LENGTH_SHORT).show(); showHome();
            }).setNegativeButton("Cancelar", null).show();
    }

    private void showSettingsInfo() {
        new AlertDialog.Builder(this).setTitle("Sobre o LISO AI")
            .setMessage("LISO AI Gaming Control Center\nVersão 1.0\n\nO aplicativo reúne diagnóstico básico e atalhos para configurações do Android. Perfis são preferências dentro do app e não fazem overclock nem encerram processos de outros aplicativos.")
            .setPositiveButton("Entendi", null).show();
    }

    private void addFeature(LinearLayout target, String number, String title, String desc, String action, Runnable callback) {
        LinearLayout card = row(); card.setGravity(Gravity.CENTER_VERTICAL); card.setPadding(dp(13),dp(14),dp(13),dp(14)); card.setBackground(round(PANEL,17));
        target.addView(card, margin(-1,-2,0,8,0,0));
        TextView n = text(number, 12, GREEN, true); n.setGravity(Gravity.CENTER); n.setBackground(round(Color.rgb(23,49,33),10)); card.addView(n,size(38,38));
        LinearLayout labels = column(); labels.setPadding(dp(12),0,dp(8),0);
        labels.addView(text(title,14,WHITE,true)); labels.addView(text(desc,10,MUTED,false), margin(-1,-2,0,4,0,0));
        card.addView(labels,new LinearLayout.LayoutParams(0,-2,1));
        TextView btn = text(action+"  ›",10,GREEN,true); btn.setGravity(Gravity.CENTER); card.addView(btn);
        card.setOnClickListener(v -> callback.run());
    }

    private LinearLayout statCard(String label, String value, String desc) {
        LinearLayout c = column(); c.setPadding(dp(14),dp(14),dp(14),dp(14)); c.setBackground(round(PANEL,17));
        c.addView(text(label,9,MUTED,true)); c.addView(text(value,19,WHITE,true),margin(-1,-2,0,8,0,0)); c.addView(text(desc,10,MUTED,false)); return c;
    }

    private View sectionTitle(String title, String sub) {
        LinearLayout r = row(); r.setGravity(Gravity.CENTER_VERTICAL);
        r.addView(text(title,14,WHITE,true), new LinearLayout.LayoutParams(0,-2,1));
        r.addView(text(sub,9,MUTED,true)); return r;
    }

    private ActivityManager.MemoryInfo getMemoryInfo() {
        try {
            ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            if (am != null) { am.getMemoryInfo(info); return info; }
        } catch (Exception ignored) { }
        return null;
    }

    private String getRamSummary() {
        ActivityManager.MemoryInfo info = getMemoryInfo();
        if (info != null) return String.format(Locale.getDefault(), "%.1f GB", info.totalMem / (1024.0 * 1024 * 1024));
        return "Indisponível";
    }

    private void runSafeOptimization() {
        long before = getCacheSize(getCacheDir());
        try { clearDirectory(getCacheDir()); } catch (Exception ignored) { }
        try { clearDirectory(getExternalCacheDir()); } catch (Exception ignored) { }
        long after = getCacheSize(getCacheDir());
        ActivityManager.MemoryInfo mem = getMemoryInfo();
        String available = mem == null ? "indisponível" : String.format(Locale.getDefault(), "%.0f%%", (mem.availMem * 100.0) / Math.max(1, mem.totalMem));
        new AlertDialog.Builder(this).setTitle("Otimização segura concluída")
            .setMessage("Cache temporário do próprio LISO AI limpo.
Memória disponível no sistema: " + available + ".

O Android controla a memória dos outros aplicativos. Para melhorar a fluidez, revise as opções de tela e bateria abaixo. Não foi encerrado nenhum jogo nem alterado o processador.")
            .setPositiveButton("Ajustes de tela", (d,w) -> openSettings(Settings.ACTION_DISPLAY_SETTINGS))
            .setNeutralButton("Bateria", (d,w) -> openSettings(Settings.ACTION_BATTERY_SAVER_SETTINGS))
            .setNegativeButton("Fechar", null).show();
    }

    private long getCacheSize(java.io.File file) {
        if (file == null || !file.exists()) return 0;
        if (file.isFile()) return file.length();
        long total = 0; java.io.File[] files = file.listFiles();
        if (files != null) for (java.io.File f : files) total += getCacheSize(f);
        return total;
    }

    private void clearDirectory(java.io.File file) {
        if (file == null || !file.exists()) return;
        java.io.File[] files = file.listFiles();
        if (files == null) return;
        for (java.io.File f : files) {
            if (f.isDirectory()) clearDirectory(f);
            try { f.delete(); } catch (Exception ignored) { }
        }
    }

    private void showGamePreparation() {
        String message = "Antes de jogar:

• Feche downloads e gravações que não usa.
• Ative Não Perturbe para evitar interrupções.
• Use taxa de atualização alta, se disponível.
• Evite jogar enquanto o aparelho estiver quente.
• Reduza gráficos no jogo se houver quedas de FPS.

O Android não permite que um app comum force desempenho máximo ou encerre processos de outros jogos.";
        new AlertDialog.Builder(this).setTitle("Preparar para jogar")
            .setMessage(message)
            .setPositiveButton("Abrir tela", (d,w) -> openSettings(Settings.ACTION_DISPLAY_SETTINGS))
            .setNeutralButton("Bateria", (d,w) -> openSettings(Settings.ACTION_BATTERY_SAVER_SETTINGS))
            .setNegativeButton("Fechar", null).show();
    }

    private void showFpsPanel() {
        LinearLayout panel = column();
        panel.setPadding(dp(20), dp(18), dp(20), dp(18));
        panel.setBackground(round(BG, 18));

        TextView title = text("LISO AI  /  FPS MONITOR", 12, GREEN, true);
        title.setLetterSpacing(.08f);
        panel.addView(title);

        float refresh = 60f;
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                refresh = getDisplay().getRefreshRate();
            } else {
                android.view.Display display = getWindowManager().getDefaultDisplay();
                refresh = display.getRefreshRate();
            }
        } catch (Exception ignored) { }

        panel.addView(text(String.format(Locale.getDefault(), "%.0f Hz", refresh), 42, WHITE, true), margin(-1,-2,0,12,0,2));
        panel.addView(text("TAXA DE ATUALIZAÇÃO DA TELA", 10, MUTED, true));

        LinearLayout bar = row();
        bar.setBackground(round(PANEL2, 8));
        View fill = new View(this);
        fill.setBackground(round(GREEN, 8));
        float ratio = Math.min(1f, refresh / 120f);
        bar.addView(fill, new LinearLayout.LayoutParams(0, dp(8), ratio));
        View remainder = new View(this);
        bar.addView(remainder, new LinearLayout.LayoutParams(0, dp(8), Math.max(.01f, 1f-ratio)));
        panel.addView(bar, margin(-1,8,0,14,0,0));

        panel.addView(text("RAM total: " + getRamSummary(), 13, WHITE, true), margin(-1,-2,0,18,0,0));
        panel.addView(text("Importante: este valor mostra a frequência da tela, não o FPS real de um jogo. O Android normalmente não permite que um app comum leia o FPS de outros jogos. Para medir o FPS do jogo, use o monitor do próprio aparelho ou uma ferramenta compatível do fabricante.", 12, MUTED, false), margin(-1,-2,0,10,0,0));

        new AlertDialog.Builder(this)
            .setTitle("Painel de FPS")
            .setView(panel)
            .setPositiveButton("Abrir ajustes de tela", (dialog, which) -> openSettings(Settings.ACTION_DISPLAY_SETTINGS))
            .setNegativeButton("Fechar", null)
            .show();
    }

    private void showDeviceInfo() {
        String model = Build.MANUFACTURER + " " + Build.MODEL;
        ActivityManager.MemoryInfo mem = getMemoryInfo();
        String available = mem == null ? "Indisponível" : String.format(Locale.getDefault(), "%.1f GB", mem.availMem / (1024.0 * 1024 * 1024));
        String info = "Aparelho: " + model + "\nAndroid: " + Build.VERSION.RELEASE + "\nSDK: " + Build.VERSION.SDK_INT + "\nRAM total: " + getRamSummary() + "\nRAM disponível: " + available + "\nPerfil LISO: " + activeProfile + "\nModo de jogo LISO: " + (gameMode ? "Ativo" : "Desativado");
        new AlertDialog.Builder(this).setTitle("Diagnóstico do aparelho").setMessage(info).setPositiveButton("Fechar", null).show();
    }

    private void openSettings(String action) {
        try { startActivity(new Intent(action)); }
        catch (Exception e) { Toast.makeText(this, "Essa configuração não está disponível neste aparelho.", Toast.LENGTH_LONG).show(); }
    }

    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l; }
    private LinearLayout row() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL); return l; }
    private TextView text(String value, int sp, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(value); t.setTextSize(sp); t.setTextColor(color); t.setIncludeFontPadding(true);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return t;
    }
    private Button primaryButton(String label) {
        Button b = new Button(this); b.setText(label); b.setTextSize(12); b.setTypeface(Typeface.DEFAULT,Typeface.BOLD); b.setAllCaps(false); b.setTextColor(BG);
        b.setBackground(round(GREEN,14)); b.setPadding(dp(10),0,dp(10),0); return b;
    }
    private GradientDrawable round(int color, int radius) { GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(dp(radius)); return d; }
    private GradientDrawable gradient(int a, int b, int radius) { GradientDrawable d = new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{a,b}); d.setCornerRadius(dp(radius)); d.setStroke(dp(1),Color.rgb(38,71,49)); return d; }
    private LinearLayout.LayoutParams size(int w, int h) { return new LinearLayout.LayoutParams(w < 0 ? w : dp(w), h < 0 ? h : dp(h)); }
    private LinearLayout.LayoutParams margin(int w,int h,int left,int top,int right,int bottom) { LinearLayout.LayoutParams p=size(w,h); p.setMargins(dp(left),dp(top),dp(right),dp(bottom)); return p; }
    private int dp(int value) { return (int)(value * getResources().getDisplayMetrics().density + 0.5f); }
}
