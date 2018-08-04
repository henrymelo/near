package br.com.caelum.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.model.Aluno;

/**
 * Created by android7164 on 07/11/17.
 */

public class AlunoDAO extends SQLiteOpenHelper {
    private static final int VERSAO = 3;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "CadastroCaelum";

    public AlunoDAO(Context context) {
        //public AlunoDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            //super(context, name, factory, version);
            //super(context, "CadastroCaelum", factory, version);
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL, "
                + " caminhoFoto TEXT, "
                + " telefone TEXT, "
                + " endereco TEXT, "
                + " site TEXT, "
                + " nota REAL);";
        database.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int versaoAntiga, int versaoNova) {
        //String sql = "DROP TABLE IF EXISTS " + TABELA;
        String sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TXT;";
        database.execSQL(sql);
        //onCreate(database);

    }

    public void insere(Aluno aluno) {
        ContentValues values = getContentValues(aluno);

        getWritableDatabase().insert(TABELA, null, values);
    }

    @NonNull
    private ContentValues getContentValues(Aluno aluno) {
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());

        return values;
    }

    public void altera(Aluno aluno) {
        ContentValues values = getContentValues(aluno);

        String[] idParaSerAlterado = { aluno.getId().toString() };
        getWritableDatabase().update(TABELA, values, " id=? ", idParaSerAlterado);
    }



    public List<Aluno> getLista() {
        List<Aluno> alunos = new ArrayList<Aluno>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABELA + ";", null);

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);

        }

        c.close();
        return alunos;
    }

    public void deletar(Aluno aluno) {
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA, " id = ? ", args);
    }

}
