# Personpage

A simple template for a personal page or resume built with [Kobweb](https://github.com/varabyte/kobweb).

You can start by simply cloning this repository, running `kobweb run` in the `editor` directory,
then putting your JSON config you got in `site/src/jsMain/resources/data.json`.

If you want to get site config from a remote source, simply edit the `CONFIG_URL` constant in
the `site/src/jsMain/kotlin/net/blusutils/bron/personpage/pages/Index.kt` file.

If you're familiar with Kobweb enough, you could also edit the layout or styling of the website: 
everything is located in the `site/src/jsMain/kotlin/net/blusutils/bron/personpage/`.

When you've finished your work, go to the `site` directory and then export your project with Static Site Generation:

```bash
$ kobweb export --layout static
```

Or look at [David Herman's blog](https://bitspittle.dev/blog/2022/static-deploy), the author of Kobweb,
for more information on how to deploy your site to a static host like GH Pages, Netlify or Vercel.

The Personpage layout is copied from my previous personal website and then rewritten in Kobweb
with support for dynamic config retrieval. I thought it would be useful for someone.

The Personpage itself written by me and by hand,
while the Editor for the config was written with 'vibe-coding' and Cursor.
Yes. Just was lazy to maintain a large config editor by own.
So, it is a single file project, it has a weird and repetitive state management.
I wasn't even touched it.
