package com.app.nextoque.controller;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.app.nextoque.R;
import com.app.nextoque.databinding.FragmentCadastrarProdutoBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NovoProdutoFragment extends Fragment {
    private static final int SELECT_PICTURES = 200;
    private FragmentCadastrarProdutoBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;
    private List<String> fotosPathList;

    public NovoProdutoFragment(NavigationView navigationView, Usuario usuario) {
        this.navigationView = navigationView;
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCadastrarProdutoBinding.inflate(inflater, container, false);

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("NOVO PRODUTO");

        fotosPathList = new ArrayList<>();

        verifyStoragePermission(getActivity());

        binding.formularioCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationView.getVisibility() == View.VISIBLE) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.btnSelecionarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(i, "Select Pictures"), SELECT_PICTURES);
            }
        });

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.salvar.setClickable(false);
                salvarProduto();
            }
        });

        return binding.getRoot();
    }

    private void salvarProduto() {
        if (binding.descricao.getEditText().getText() == null ||
                binding.descricao.getEditText().getText().toString().trim().isEmpty() ||
                binding.categoria.getEditText().getText() == null ||
                binding.categoria.getEditText().getText().toString().trim().isEmpty() ||
                binding.unidadeMedida.getEditText().getText() == null ||
                binding.unidadeMedida.getEditText().getText().toString().trim().isEmpty() ||
                binding.quantidade.getEditText().getText() == null ||
                binding.quantidade.getEditText().getText().toString().trim().isEmpty()) {

            Toast.makeText(getContext(), "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> params =  new HashMap<>();

            params.put("descricao", binding.descricao.getEditText().getText().toString());
            params.put("categoria", binding.categoria.getEditText().getText().toString());
            params.put("unidadeMedida", binding.unidadeMedida.getEditText().getText().toString());

            Long quantidade = Long.valueOf(binding.quantidade.getEditText().getText().toString());

            params.put("quantidade", quantidade);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

            Date data = Calendar.getInstance().getTime();

            params.put("data", dateFormat.format(data));
            params.put("hora", timeFormat.format(data));
            params.put("obs", binding.observacao.getEditText().getText() != null ? binding.observacao.getEditText().getText().toString() : null);

            params.put("idUsuario", FirebaseAuth.getInstance().getUid());

            new ProdutoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).salvarProduto(params, fotosPathList, navigationView, binding.salvar);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.

                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();

                            fotosPathList.add(getPathFromUri(getContext(), imageUri));
                        }

                        binding.fotosSelecionadas.setText(count + " fotos selecionadas");
                    } else if (data.getData() != null) {
                        Uri imageUri = data.getData();

                        fotosPathList.add(getPathFromUri(getContext(), imageUri));

                        binding.fotosSelecionadas.setText("1 foto selecionada");
                    }
                }
            }
        }

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else { // handle non-primary volumes
                    return Environment.getExternalStorageDirectory().getParentFile().getParentFile() + "/" + type + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
