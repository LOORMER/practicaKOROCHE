package ru.btpit.nmedia

import java.util.Calendar

fun getPosts():List<Post>{
    return listOf(
        Post(
            id = 2,
            header = "АСМР",
            content = "АСМР (автономная сенсорная меридиональная реакция) — это, по данным Кембриджского словаря, " +
                    "приятное покалывание, которое люди чувствуют в ответ на звуковые и визуальные стимулы (шепот, " +
                    "движения кисти, жевание, скрежет, постукивания и т.д.). Это ощущение еще может сопровождаться чувством " +
                    "эйфории и расслаблением. В последние годы в сети Интернет поклонников этого веяния становится все больше.",
            dataTime = Calendar.getInstance().time,
            amountlike = randomNumb(),
            amountrepost = randomNumb(),
            amountglaza = randomNumb(),
            isLike = false,
            isRepos = false,
        ),
        Post(
            id = 1,
            header = "Адаптеры в Android",
            content = "Адаптеры в Android упрощают связывание данных с элементом управления. Они используются при работе с виджетами, которые дополняют android.widget.AdapterView: ListView, ExpandableListView, GridView, Spinner, Gallery, а также в активности ListActivity и др.\n" +
                    "\n" +
                    "Примеры готовых адаптеров:\n" +
                    "   ArrayAdapter<T> — предназначен для работы с ListView.\n" +
                    "   ListAdapter — адаптер между ListView и данными.\n" +
                    "   SpinnerAdapter — адаптер для связки данных с элементом Spinner.\n" +
                    "   SimpleAdapter — адаптер, позволяющий заполнить данными список более сложной структуры.\n" +
                    "   Если вам нужен собственный адаптер, в Android есть абстрактный класс BaseAdapter, который можно расширить.",
            dataTime = Calendar.getInstance().time,
            amountlike = randomNumb(),
            amountrepost = randomNumb(),
            amountglaza = randomNumb(),
            isRepos = false,
            isLike = false,

            ))

}
fun getEmptyPost():Post{
    return Post(
        0,

        "",
        "",
        dataTime = Calendar.getInstance().time,
        amountlike = 0,
        amountglaza = 0,
        amountrepost = 0,
        isRepos = false,
        isLike = false,
    )
}